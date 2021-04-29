using System;
using System.Collections.Generic;
using NpgsqlTypes;

#nullable disable

namespace WebApplication
{
    public partial class Airport
    {
        public string AirportCode { get; set; }
        public string AirportName { get; set; }
        public string City { get; set; }
        public NpgsqlPoint Coordinates { get; set; }
        public string Timezone { get; set; }
    }
}
