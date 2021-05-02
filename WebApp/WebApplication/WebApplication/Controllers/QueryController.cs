using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
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
        public async Task<ActionResult<IEnumerable<KeyValuePair<string,int>>>> Route([FromQuery] string fromAirport, [FromQuery] string toAirport, [FromQuery] DateTime date,[FromQuery] string clazz, [FromQuery] int limit)
        {
            var flights = from route in db.Flights
                          where route.DepartureAirport == fromAirport
                          where route.ArrivalAirport == toAirport
                          where route.ScheduledDeparture.Date >= date && route.ScheduledDeparture.Date <= date.AddDays(1)
                          select route;

            var ticketWithNecessaryClazz = from ticketFlight in db.TicketFlights
                                           where ticketFlight.FareConditions == clazz
                                           select ticketFlight;
            var joined = from fl in flights
                         join ticketFlight in ticketWithNecessaryClazz on fl.FlightId equals ticketFlight.FlightId

                         select new
                         {
                             Flight = fl,
                             TicketFlight = ticketFlight
                         };
            var grouped = from g in joined
                          group g by g.Flight.FlightId;
            var ticketsCounter = from g in grouped
                         select new KeyValuePair<int,int>(g.Key, g.Count());

            var plainFlightPair = from plain in db.AircraftsData
                         join flight in flights on plain.AircraftCode equals flight.AircraftCode
                         select new
                         {
                            Flight = flight,
                            Plain = plain
                         };

            var seats = from pair in plainFlightPair
                                   join seates in db.Seats on pair.Plain.AircraftCode equals seates.AircraftCode
                                   where seates.FareConditions == clazz
                                   group seates by seates.AircraftCode;
            var seatsCounter = from s in seats
                               select new KeyValuePair<string,int>(s.Key, s.Count());


            /*var r1 = from pair in plainFlightPair
                     join tc in ticketsCounter on pair.Flight.FlightId equals tc.Key
                     //where tc.Elements[0] > 0
                     select pair.Flight.FlightId;*/
            
            
            /*var res = from pair in plainFlightPair
                      join sc in seatsCounter on pair.Plain.AircraftCode equals sc.Key
                      join tc in ticketsCounter on pair.Flight.FlightId equals tc.Key
                      where Math.Abs(sc.Value - tc.Value) > 0
                      select pair.Flight.FlightId;*///new HelpClasses.Flight(pair.Flight.ScheduledArrival.ToString(), pair.Flight.ScheduledArrival.ToString(), pair.Flight.FlightNo, pair.Flight.DepartureAirport, pair.Flight.ArrivalAirport) ;



            return await seatsCounter.ToListAsync();
        
        }

        [HttpGet]
        [ActionName("booking")]

        public bool Book()
        {


            return true;
        
        }

        [HttpGet]
        [ActionName("check")]
        public async Task<ActionResult<IEnumerable<Ticket>>> Check(string passengerID)
        {
            var res = from ticket in db.Tickets
                      where ticket.PassengerId == passengerID
                      select ticket;
            if (res != null)
            {
                return await res.ToListAsync();
            }
            else
            {
                return null; 
            }

        
        }








    }
}
