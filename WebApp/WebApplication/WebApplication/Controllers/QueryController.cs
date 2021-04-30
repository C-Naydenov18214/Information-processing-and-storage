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
    [Route("api/[controller]")]
    //[ApiController]
    public class QueryController : ControllerBase
    {
        DemoContext db;

        public QueryController(DemoContext context)
        {
            db = context;
        }

        //[HttpGet]
        [ActionName("Cities")]
        public async Task<ActionResult<IEnumerable<string>>> Cities()
        {
            var res = from port in db.AirportsData
                      orderby port.City
                      select port.City;

            return await res.ToListAsync();

        }

        //[HttpGet]
        [ActionName("Airports")]
        public async Task<ActionResult<IEnumerable<string>>> Airports()
        {
            var res = from port in db.AirportsData
                      orderby port.AirportName
                      select port.AirportName;

            return await res.ToListAsync();

        }

        //[HttpGet]
        [ActionName("CityAirports")]
        public IEnumerable<OneToMany<string, string>> CityAirports()
        {
            var res = from port in db.AirportsData
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

        //[HttpGet]
        [ActionName("Inbound")]
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
        //[HttpGet]
        [ActionName("Outbound")]
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


        //[HttpGet("{from}")]
        [ActionName("Route")]
        public string Route([FromQuery] string from, [FromQuery] string to)
        {
            return $"from {from} to {to}";
        
        }








    }
}
