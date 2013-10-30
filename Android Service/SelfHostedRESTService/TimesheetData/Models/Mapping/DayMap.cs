using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;

namespace TimesheetData.Models.Mapping
{
    public class DayMap : EntityTypeConfiguration<Day>
    {
        public DayMap()
        {
            // Primary Key
            this.HasKey(t => new { t.EmployeeEmail, t.Date });

            // Properties
            this.Property(t => t.EmployeeEmail)
                .IsRequired()
                .HasMaxLength(255);

            this.Property(t => t.Comment)
                .HasMaxLength(1000);

            // Table & Column Mappings
            this.ToTable("Days");
            this.Property(t => t.EmployeeEmail).HasColumnName("EmployeeEmail");
            this.Property(t => t.Date).HasColumnName("Date");
            this.Property(t => t.TimeStart).HasColumnName("TimeStart");
            this.Property(t => t.TimeFinish).HasColumnName("TimeFinish");
            this.Property(t => t.LunchHours).HasColumnName("LunchHours");
            this.Property(t => t.HolidayHours).HasColumnName("HolidayHours");
            this.Property(t => t.TrainingHours).HasColumnName("TrainingHours");
            this.Property(t => t.Illness).HasColumnName("Illness");
            this.Property(t => t.Research).HasColumnName("Research");
            this.Property(t => t.Comment).HasColumnName("Comment");

            // Relationships
            this.HasRequired(t => t.Employee)
                .WithMany(t => t.Days)
                .HasForeignKey(d => d.EmployeeEmail);

        }
    }
}
