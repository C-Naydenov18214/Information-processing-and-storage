using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WebApplication.Controllers.HelpClasses;

namespace WebApplication.Controllers
{
    [Route("query/[action]")]
    //[ApiController]
    public class QueryController : ControllerBase
    {
        DemoContext db;

        public QueryController(DemoContext context)
        {
            db = context;
        }

        [HttpGet]
        [ActionName("cities")]
        public async Task<ActionResult<IEnumerable<string>>> Cities()
        {
            var res = from port in db.AirportsData
                      orderby port.City
                      select port.City;

            return await res.ToListAsync();

        }

        [HttpGet]
        [ActionName("airports")]
        public async Task<ActionResult<IEnumerable<string>>> Airports()
        {
            var res = from port in db.AirportsData
                      orderby port.AirportName
                      select port.AirportName;

            return await res.ToListAsync();

        }

        [HttpGet]
        [ActionName("cityAirports")]
        public IEnumerable<OneToMany<string, string>> CityAirports([FromQuery] string city)
        {
            var res = from port in db.AirportsData
                      where port.City == city
                      orderby port.AirportName
                      select new
                      {
                          City = port.City,
                          Airport = port.AirportName
                      };
            Dictionary<string, OneToMany<string, string>> dict = new Dictionary<string, OneToMany<string, string>>();
            foreach (var c in res)
            {
                if (dict.ContainsKey(c.City))
                {
                    dict[c.City].Elements.Add(c.Airport);
                }
                else
                {
                    dict.Add(c.City, new OneToMany<string, string>(c.City, c.Airport));
                }

            }

            return dict.Values.ToList();
        }

        [HttpGet]
        [ActionName("inbound")]
        public IEnumerable<OneToMany<string, HelpClasses.Flight>> Inbound([FromQuery] string airport)
        {
            var res = from flight in db.Flights
                      where flight.ArrivalAirport == airport
                      select new HelpClasses.Flight(flight.ScheduledArrival.ToString(), flight.ScheduledArrival.ToString(), flight.FlightNo, flight.DepartureAirport, airport);



            Dictionary<string, OneToMany<string, HelpClasses.Flight>> dict = new Dictionary<string, OneToMany<string, HelpClasses.Flight>>();
            foreach (var c in res)
            {
                if (dict.ContainsKey(c.To))
                {
                    dict[c.To].Elements.Add(c);
                }
                else
                {
                    dict.Add(c.To, new OneToMany<string, HelpClasses.Flight>(c.To, c));
                }

            }
            return dict.Values.ToList();
        }
        [HttpGet]
        [ActionName("outbound")]
        public IEnumerable<OneToMany<string, HelpClasses.Flight>> Outbound([FromQuery] string airport)
        {
            var res = from flight in db.Flights
                      where flight.DepartureAirport == airport
                      select new HelpClasses.Flight(flight.ScheduledArrival.ToString(), flight.ScheduledArrival.ToString(), flight.FlightNo, airport, flight.ArrivalAirport);



            Dictionary<string, OneToMany<string, HelpClasses.Flight>> dict = new Dictionary<string, OneToMany<string, HelpClasses.Flight>>();
            foreach (var c in res)
            {
                if (dict.ContainsKey(c.From))
                {
                    dict[c.From].Elements.Add(c);
                }
                else
                {
                    dict.Add(c.From, new OneToMany<string, HelpClasses.Flight>(c.From, c));
                }

            }
            return dict.Values.ToList();
        }


        [HttpGet]
        [ActionName("route")]
        public async Task<ActionResult<IEnumerable<KeyValuePair<KeyValuePair<string, string>, int>>>> Route([FromQuery] string fromAirport, [FromQuery] string toAirport, [FromQuery] DateTime date, [FromQuery] string clazz)
        {

            //нужный рейс
            var flights = from route in db.Flights
                          where route.DepartureAirport == fromAirport
                          where route.ArrivalAirport == toAirport
                          where route.ScheduledDeparture.Date >= date && route.ScheduledDeparture.Date <= date.AddDays(1)
                          select route;


            //нужнйы самолет
            var plainFlightPair = from plain in db.AircraftsData
                                  join flight in flights on plain.AircraftCode equals flight.AircraftCode
                                  select new
                                  {
                                      Flight = flight,
                                      Plain = plain
                                  };



            var tickets = from pair in plainFlightPair
                          join t in db.FreeTickets on pair.Plain.AircraftCode equals t.AircraftCode
                          where t.FareConditions == clazz
                          where t.Counter > 0
                          select new KeyValuePair<KeyValuePair<string, string>, int>(new KeyValuePair<string, string>(pair.Flight.FlightNo, pair.Plain.AircraftCode), t.Counter.Value);




            return await tickets.ToListAsync();

        }

        [HttpGet]
        [ActionName("find-path")]
        public List<List<KeyValuePair<KeyValuePair<string, string>, string>>> FindPath([FromQuery] string fromAirport, [FromQuery] string toAirport, [FromQuery] DateTime date, [FromQuery] string clazz = null, [FromQuery] int limit = 0)
        {

            var curState = from src in db.Flights
                           where src.DepartureAirport == fromAirport
                           where src.ScheduledDeparture.Date >= date && src.ScheduledDeparture.Date <= date.AddDays(1)
                           select new
                           {
                               From = src.ArrivalAirport,
                               Path = src.FlightId.ToString()

                           };//new OneToMany<string, int>($"{fromPoint}->{toPoint}", src.FlightId);

            limit--;
            while (limit > -1)
            {
                curState = from cur in curState
                           join s in db.Flights on cur.From equals s.DepartureAirport
                           select new
                           {
                               From = s.ArrivalAirport,
                               Path = $"{cur.Path}/{s.FlightId}"
                           };
                limit--;
            }
            var res = from c in curState
                      where c.From == toAirport
                      select c.Path;

            var ls = res.ToList();
 
            var routes = ls.Select(e => e.Split('/'));
            List<List<KeyValuePair<KeyValuePair<string, string>, string>>> result = new List<List<KeyValuePair<KeyValuePair<string, string>, string>>>();
            var empty = false;
            foreach (var route in routes)
            {
                var cur = new List<KeyValuePair<KeyValuePair<string, string>, string>>();
                foreach (var flight in route)
                {
                    var t = GetTicket(Int32.Parse(flight), clazz);
                    if (t == null)
                    {
                        empty = true;
                        break;
                    }
                    else
                    {
                        cur.AddRange(t);
                    }
                }
                if (!empty)
                {
                    result.Add(cur);
                }
                empty = false;
            }
            
            return result;

        }
        [HttpGet]
        [ActionName("show-path")]
        public IEnumerable<string> ShowPath([FromQuery] string fromAirport, [FromQuery] string toAirport, [FromQuery] DateTime date, [FromQuery] string clazz = null, [FromQuery] int limit = 0)
        {

            var curState = from src in db.Flights
                           where src.DepartureAirport == fromAirport
                           where src.ScheduledDeparture.Date >= date && src.ScheduledDeparture.Date <= date.AddDays(1)
                           select new
                           {
                               From = src.ArrivalAirport,
                               Path = src.FlightId.ToString()

                           };//new OneToMany<string, int>($"{fromPoint}->{toPoint}", src.FlightId);

            var oldState = curState;
            limit--;
            while (limit > -1)
            {
                curState = from cur in curState
                           join s in db.Flights on cur.From equals s.DepartureAirport
                           select new
                           {
                               From = s.ArrivalAirport,
                               Path = $"{cur.Path}/{s.FlightId}"
                           };
                limit--;
            }
            var res = from c in curState
                      where c.From == toAirport
                      select c.Path;

            return res.ToList();
        }


        private IEnumerable<KeyValuePair<KeyValuePair<string, string>, string>> GetTicket(int flightID,string clazz = null)
        {
            var flights = from route in db.Flights
                          where route.FlightId == flightID
                          select route;


            //нужнйы самолет
            var plainFlightPair = from plain in db.AircraftsData
                                  join flight in flights on plain.AircraftCode equals flight.AircraftCode
                                  select new
                                  {
                                      Flight = flight,
                                      Plain = plain
                                  };


            if (clazz == null)
            {
                var tickets = from pair in plainFlightPair
                              join t in db.FreeTickets on pair.Plain.AircraftCode equals t.AircraftCode
                              where t.Counter > 0
                              select new KeyValuePair<KeyValuePair<string, string>, string>(new KeyValuePair<string, string>($"Flight ID:{pair.Flight.FlightId} Flight No:{pair.Flight.FlightNo}", $"Aircraft code: {pair.Plain.AircraftCode}"), $"Free {t.FareConditions} seats {t.Counter.Value}");
                return tickets;
            }
            else
            {
                var tickets = from pair in plainFlightPair
                              join t in db.FreeTickets on pair.Plain.AircraftCode equals t.AircraftCode
                              where t.FareConditions == clazz
                              where t.Counter > 0
                              select new KeyValuePair<KeyValuePair<string, string>, string>(new KeyValuePair<string, string>($"Flight ID:{pair.Flight.FlightId} Flight №{pair.Flight.FlightNo}", $"Aircraft code: {pair.Plain.AircraftCode}"), $"Free {t.FareConditions} seats {t.Counter.Value}");
                return tickets;

            }


        }
        
        
        [HttpGet]
        [ActionName("book-all")]
        public async Task<ActionResult<IEnumerable<TicketFlight>>> BookAll(string routes,string passengerName, string fareConditons)
        {
            List<TicketFlight> tickets = new List<TicketFlight>();
            var flights = routes.Split('/');
            var booking = new Booking();
            var bookGuid = Guid.NewGuid().ToString();
            booking.BookDate = DateTime.Now;
            booking.BookRef = new string(bookGuid.Take(6).ToArray());
            foreach (var f in flights)
            {
                var route = Int32.Parse(f);

                var prices = from t in db.Prices
                             where t.FlightId == route
                             where t.FareConditions == fareConditons
                             select t.Price1;


                var guid = Guid.NewGuid().ToString();
                var flight = from fl in db.Flights
                             where fl.FlightId == route
                             select fl;
                booking.TotalAmount += prices.FirstOrDefault();
                var ticket = new Ticket();
                ticket.BookRef = booking.BookRef;
                ticket.PassengerName = passengerName;
                ticket.PassengerId = new string(guid.Take(10).ToArray());
                ticket.TicketNo = new string(guid.Take(13).ToArray());
                var ticketFlight = new TicketFlight();
                ticketFlight.Amount = prices.FirstOrDefault();
                ticketFlight.TicketNo = ticket.TicketNo;
                ticketFlight.FlightId = route;
                ticketFlight.FareConditions = fareConditons;

                db.Tickets.Add(ticket);
                db.TicketFlights.Add(ticketFlight);
                UpdateTickets(route, fareConditons);
                tickets.Add(ticketFlight);
                
            }
            db.Bookings.Add(booking);
            await db.SaveChangesAsync();
            return Ok(tickets);

        }
        [HttpGet]
        [ActionName("booking")]

        public async Task<ActionResult<TicketFlight>> Book(int route, string passengerName, string fareConditons)
        {

            var prices = from t in db.Prices
                         where t.FlightId == route
                         where t.FareConditions == fareConditons
                         select t.Price1;


            var guid = Guid.NewGuid().ToString();
            var flight = from fl in db.Flights
                         where fl.FlightId == route
                         select fl;



            var booking = new Booking();
            booking.BookRef = new string(guid.Take(6).ToArray());
            booking.BookDate = DateTime.Now;
            booking.TotalAmount = prices.FirstOrDefault();
            var ticket = new Ticket();
            ticket.BookRef = booking.BookRef;
            ticket.PassengerName = passengerName;
            ticket.PassengerId = new string(guid.Take(10).ToArray());
            ticket.TicketNo = new string(guid.Take(13).ToArray());
            var ticketFlight = new TicketFlight();
            ticketFlight.Amount = booking.TotalAmount;
            ticketFlight.TicketNo = ticket.TicketNo;
            ticketFlight.FlightId = route;
            ticketFlight.FareConditions = fareConditons;

            db.Bookings.Add(booking);
            db.Tickets.Add(ticket);
            db.TicketFlights.Add(ticketFlight);
            UpdateTickets(route, fareConditons);
            await db.SaveChangesAsync();


            return Ok(ticketFlight);

        }

        private void UpdateTickets(int id, string conditions)
        {

            var plain = from r in db.Flights
                        where r.FlightId == id
                        select r.AircraftCode;
            var t = from p in db.FreeTickets
                    where p.AircraftCode == plain.FirstOrDefault()
                    where p.FareConditions == conditions
                    select p;
            var ticket = t.FirstOrDefault();
            ticket.Counter--;
        }

        [HttpGet]
        [ActionName("check-in")]
        public async Task<ActionResult<BoardingPass>> Check(int flightID, string ticketNo)
        {
            var random = new Random();

            var boardingPass = new BoardingPass();
            boardingPass.TicketNo = ticketNo;
            boardingPass.FlightId = flightID;
            boardingPass.BoardingNo = random.Next(0, Int16.MaxValue);
            var conditions = from t in db.TicketFlights
                             where t.TicketNo == ticketNo
                             where t.FlightId == flightID
                             select t.FareConditions;

            var seats = from s in db.Seats
                        where s.IsFree == "yes"
                        where s.FareConditions == conditions.FirstOrDefault()
                        select s;
            var seat = seats.FirstOrDefault();
            boardingPass.SeatNo = seat.SeatNo;
            db.BoardingPasses.Add(boardingPass);
            seat.IsFree = "no";
            await db.SaveChangesAsync();
            return boardingPass;

        }








    }
}
