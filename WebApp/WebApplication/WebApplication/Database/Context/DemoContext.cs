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
        public virtual DbSet<FreeTicket> FreeTickets { get; set; }
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
                entity.HasKey(e => e.AircraftCode)
                    .HasName("aircrafts_pkey");

                entity.ToTable("aircrafts_data");

                entity.Property(e => e.AircraftCode)
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
                entity.HasKey(e => e.AirportCode)
                    .HasName("airports_data_pkey");

                entity.ToTable("airports_data");

                entity.Property(e => e.AirportCode)
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
                entity.HasKey(e => new { e.TicketNo, e.FlightId })
                    .HasName("boarding_passes_pkey");

                entity.ToTable("boarding_passes");

                entity.Property(e => e.TicketNo)
                    .HasMaxLength(13)
                    .HasColumnName("ticket_no")
                    .IsFixedLength(true);

                entity.Property(e => e.FlightId).HasColumnName("flight_id");

                entity.Property(e => e.BoardingNo).HasColumnName("boarding_no");

                entity.Property(e => e.SeatNo)
                    .IsRequired()
                    .HasMaxLength(4)
                    .HasColumnName("seat_no");
            });

            modelBuilder.Entity<Booking>(entity =>
            {
                entity.HasKey(e => e.BookRef)
                    .HasName("bookings_pkey");

                entity.ToTable("bookings");

                entity.Property(e => e.BookRef)
                    .HasMaxLength(6)
                    .HasColumnName("book_ref")
                    .IsFixedLength(true);

                entity.Property(e => e.BookDate)
                    .HasColumnType("timestamp with time zone")
                    .HasColumnName("book_date");

                entity.Property(e => e.TotalAmount)
                    .HasPrecision(10, 2)
                    .HasColumnName("total_amount");
            });

            modelBuilder.Entity<Flight>(entity =>
            {
                entity.ToTable("flights");

                entity.HasIndex(e => new { e.FlightNo, e.ScheduledDeparture }, "flights_flight_no_scheduled_departure_key")
                    .IsUnique();

                entity.Property(e => e.FlightId)
                    .ValueGeneratedNever()
                    .HasColumnName("flight_id");

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

            modelBuilder.Entity<FreeTicket>(entity =>
            {
                entity.HasKey(e => new { e.AircraftCode, e.FareConditions })
                    .HasName("free_tickets_pkey");

                entity.ToTable("free_tickets");

                entity.Property(e => e.AircraftCode)
                    .HasMaxLength(3)
                    .HasColumnName("aircraft_code")
                    .IsFixedLength(true);

                entity.Property(e => e.FareConditions)
                    .HasMaxLength(10)
                    .HasColumnName("fare_conditions");

                entity.Property(e => e.Counter).HasColumnName("counter");
            });

            modelBuilder.Entity<Seat>(entity =>
            {
                entity.HasKey(e => new { e.AircraftCode, e.SeatNo })
                    .HasName("seats_pkey");

                entity.ToTable("seats");

                entity.Property(e => e.AircraftCode)
                    .HasMaxLength(3)
                    .HasColumnName("aircraft_code")
                    .IsFixedLength(true);

                entity.Property(e => e.SeatNo)
                    .HasMaxLength(4)
                    .HasColumnName("seat_no");

                entity.Property(e => e.FareConditions)
                    .IsRequired()
                    .HasMaxLength(10)
                    .HasColumnName("fare_conditions");

                entity.Property(e => e.IsFree)
                    .HasMaxLength(3)
                    .HasColumnName("is_free")
                    .HasDefaultValueSql("'yes'::character varying");
            });

            modelBuilder.Entity<Ticket>(entity =>
            {
                entity.HasKey(e => e.TicketNo)
                    .HasName("tickets_pkey");

                entity.ToTable("tickets");

                entity.Property(e => e.TicketNo)
                    .HasMaxLength(13)
                    .HasColumnName("ticket_no")
                    .IsFixedLength(true);

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
            });

            modelBuilder.Entity<TicketFlight>(entity =>
            {
                entity.HasKey(e => new { e.TicketNo, e.FlightId })
                    .HasName("ticket_flights_pkey");

                entity.ToTable("ticket_flights");

                entity.Property(e => e.TicketNo)
                    .HasMaxLength(13)
                    .HasColumnName("ticket_no")
                    .IsFixedLength(true);

                entity.Property(e => e.FlightId).HasColumnName("flight_id");

                entity.Property(e => e.Amount)
                    .HasPrecision(10, 2)
                    .HasColumnName("amount");

                entity.Property(e => e.FareConditions)
                    .IsRequired()
                    .HasMaxLength(10)
                    .HasColumnName("fare_conditions");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
