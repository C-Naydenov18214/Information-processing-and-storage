using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Controllers.HelpClasses
{
    public class CityAirports
    {
        public string CityName { get; }
        public List<string> Airports { get; }

        public CityAirports(string city, string airport)
        {
            CityName = city;
            Airports = new List<string>();
            Airports.Add(airport);
        }


    }
}
