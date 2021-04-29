using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace WebApplication
{
    public partial class DemoContext : DbContext
    {
        public DemoContext()
        {
        }

        public DemoContext(DbContextOptions<DemoContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Aircraft> AircraftsData { get; set; }
        public virtual DbSet<Airport> AirportsData { get; set; }
        public virtual DbSet<BoardingPass> BoardingPasses { get; set; }
        public virtual DbSet<Booking> Bookings { get; set; }
        public virtual DbSet<Flight> Flights { get; set; }
        public virtual DbSet<Seat> Seats { get; set; }
        public virtual DbSet<Ticket> Tickets { get; set; }
        public virtual DbSet<TicketFlight> TicketFlights { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                optionsBuilder.UseNpgsql("Host=localhost;Port=5432;Database=demo;Username=postgres;Password=123123");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasAnnotation("Relational:Collation", "Russian_Russia.1251");

            modelBuilder.Entity<Aircraft>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("aircrafts_data");

                entity.Property(e => e.AircraftCode)
                    .IsRequired()
                    .HasMaxLength(3)
                    .HasColumnName("aircraft_code")
                    .IsFixedLength(true);

                entity.Property(e => e.Model)
                    .IsRequired()
                    .HasMaxLength(50)
                    .HasColumnName("model");

                entity.Property(e => e.Range).HasColumnName("range");
            });

            modelBuilder.Entity<Airport>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("airports_data");

                entity.Property(e => e.AirportCode)
                    .IsRequired()
                    .HasMaxLength(3)
                    .HasColumnName("airport_code")
                    .IsFixedLength(true);

                entity.Property(e => e.AirportName)
                    .IsRequired()
                    .HasMaxLength(50)
                    .HasColumnName("airport_name");

                entity.Property(e => e.City)
                    .IsRequired()
                    .HasMaxLength(50)
                    .HasColumnName("city");

                entity.Property(e => e.Coordinates).HasColumnName("coordinates");

                entity.Property(e => e.Timezone)
                    .IsRequired()
                    .HasColumnName("timezone");
            });

            modelBuilder.Entity<BoardingPass>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("boarding_passes");

                entity.Property(e => e.BoardingNo).HasColumnName("boarding_no");

                entity.Property(e => e.FlightId).HasColumnName("flight_id");

                entity.Property(e => e.SeatNo)
                    .IsRequired()
                    .HasMaxLength(4)
                    .HasColumnName("seat_no");

                entity.Property(e => e.TicketNo)
                    .IsRequired()
                    .HasMaxLength(13)
                    .HasColumnName("ticket_no")
                    .IsFixedLength(true);
            });

            modelBuilder.Entity<Booking>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("bookings");

                entity.Property(e => e.BookDate)
                    .HasColumnType("timestamp with time zone")
                    .HasColumnName("book_date");

                entity.Property(e => e.BookRef)
                    .IsRequired()
                    .HasMaxLength(6)
                    .HasColumnName("book_ref")
                    .IsFixedLength(true);

                entity.Property(e => e.TotalAmount)
                    .HasPrecision(10, 2)
                    .HasColumnName("total_amount");
            });

            modelBuilder.Entity<Flight>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("flights");

                entity.Property(e => e.ActualArrival)
                    .HasColumnType("timestamp with time zone")
                    .HasColumnName("actual_arrival");

                entity.Property(e => e.ActualDeparture)
                    .HasColumnType("timestamp with time zone")
                    .HasColumnName("actual_departure");

                entity.Property(e => e.AircraftCode)
                    .IsRequired()
                    .HasMaxLength(3)
                    .HasColumnName("aircraft_code")
                    .IsFixedLength(true);

                entity.Property(e => e.ArrivalAirport)
                    .IsRequired()
                    .HasMaxLength(3)
                    .HasColumnName("arrival_airport")
                    .IsFixedLength(true);

                entity.Property(e => e.DepartureAirport)
                    .IsRequired()
                    .HasMaxLength(3)
                    .HasColumnName("departure_airport")
                    .IsFixedLength(true);

                entity.Property(e => e.FlightId).HasColumnName("flight_id");

                entity.Property(e => e.FlightNo)
                    .IsRequired()
                    .HasMaxLength(6)
                    .HasColumnName("flight_no")
                    .IsFixedLength(true);

                entity.Property(e => e.ScheduledArrival)
                    .HasColumnType("timestamp with time zone")
                    .HasColumnName("scheduled_arrival");

                entity.Property(e => e.ScheduledDeparture)
                    .HasColumnType("timestamp with time zone")
                    .HasColumnName("scheduled_departure");

                entity.Property(e => e.Status)
                    .IsRequired()
                    .HasMaxLength(20)
                    .HasColumnName("status");
            });

            modelBuilder.Entity<Seat>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("seats");

                entity.Property(e => e.AircraftCode)
                    .IsRequired()
                    .HasMaxLength(3)
                    .HasColumnName("aircraft_code")
                    .IsFixedLength(true);

                entity.Property(e => e.FareConditions)
                    .IsRequired()
                    .HasMaxLength(10)
                    .HasColumnName("fare_conditions");

                entity.Property(e => e.SeatNo)
                    .IsRequired()
                    .HasMaxLength(4)
                    .HasColumnName("seat_no");
            });

            modelBuilder.Entity<Ticket>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("tickets");

                entity.Property(e => e.BookRef)
                    .IsRequired()
                    .HasMaxLength(6)
                    .HasColumnName("book_ref")
                    .IsFixedLength(true);

                entity.Property(e => e.ContactData)
                    .HasColumnType("jsonb")
                    .HasColumnName("contact_data");

                entity.Property(e => e.PassengerId)
                    .IsRequired()
                    .HasMaxLength(20)
                    .HasColumnName("passenger_id");

                entity.Property(e => e.PassengerName).HasColumnName("passenger_name");

                entity.Property(e => e.TicketNo)
                    .IsRequired()
                    .HasMaxLength(13)
                    .HasColumnName("ticket_no")
                    .IsFixedLength(true);
            });

            modelBuilder.Entity<TicketFlight>(entity =>
            {
                entity.HasNoKey();

                entity.ToTable("ticket_flights");

                entity.Property(e => e.Amount)
                    .HasPrecision(10, 2)
                    .HasColumnName("amount");

                entity.Property(e => e.FareConditions)
                    .IsRequired()
                    .HasMaxLength(10)
                    .HasColumnName("fare_conditions");

                entity.Property(e => e.FlightId).HasColumnName("flight_id");

                entity.Property(e => e.TicketNo)
                    .IsRequired()
                    .HasMaxLength(13)
                    .HasColumnName("ticket_no")
                    .IsFixedLength(true);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
