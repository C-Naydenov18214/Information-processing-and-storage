using System;
using System.Collections.Generic;

#nullable disable

namespace WebApplication
{
    public partial class Flight
    {
        public int FlightId { get; set; }
        public string FlightNo { get; set; }
        public DateTime ScheduledDeparture { get; set; }
        public DateTime ScheduledArrival { get; set; }
        public string DepartureAirport { get; set; }
        public string ArrivalAirport { get; set; }
        public string Status { get; set; }
        public string AircraftCode { get; set; }
        public DateTime? ActualDeparture { get; set; }
        public DateTime? ActualArrival { get; set; }
    }
}
