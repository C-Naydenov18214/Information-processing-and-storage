using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Controllers.HelpClasses
{
    public class Flight
    {
        public string Date { get; }
        public string Time { get; }
        public string FlightNum { get; }

        public string From { get; }
        public string To { get; }

        public Flight(string date, string time, string num, string from,string to)
        {
            Date = date;
            Time = time;
            FlightNum = num;
            From = from;
            To = to;
        }

    }
}
