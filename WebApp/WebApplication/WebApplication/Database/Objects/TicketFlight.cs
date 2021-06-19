using System;
using System.Collections.Generic;

#nullable disable

namespace WebApplication
{
    public partial class TicketFlight
    {
        public string TicketNo { get; set; }
        public int FlightId { get; set; }
        public string FareConditions { get; set; }
        public decimal Amount { get; set; }
    }
}
