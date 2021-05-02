using System;
using System.Collections.Generic;

#nullable disable

namespace WebApplication
{
    public partial class Booking
    {
        public string BookRef { get; set; }
        public DateTime BookDate { get; set; }
        public decimal TotalAmount { get; set; }


        public Booking(string bookRef, DateTime bookDate, decimal amount)
        {
            BookRef = bookRef;
            BookDate = bookDate;
            TotalAmount = amount;
        }
    }
}
