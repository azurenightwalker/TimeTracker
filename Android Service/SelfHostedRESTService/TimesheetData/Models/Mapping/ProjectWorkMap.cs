using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;

namespace TimesheetData.Models.Mapping
{
    public class ProjectWorkMap : EntityTypeConfiguration<ProjectWork>
    {
        public ProjectWorkMap()
        {
            // Primary Key
            this.HasKey(t => new { t.EmployeeEmail, t.Date, t.Project });

            // Properties
            this.Property(t => t.EmployeeEmail)
                .IsRequired()
                .HasMaxLength(255);

            this.Property(t => t.Project)
                .IsRequired()
                .HasMaxLength(50);

            // Table & Column Mappings
            this.ToTable("ProjectWork");
            this.Property(t => t.EmployeeEmail).HasColumnName("EmployeeEmail");
            this.Property(t => t.Date).HasColumnName("Date");
            this.Property(t => t.Project).HasColumnName("Project");
            this.Property(t => t.Dev).HasColumnName("Dev");
            this.Property(t => t.Support).HasColumnName("Support");
            this.Property(t => t.Sales).HasColumnName("Sales");
            this.Property(t => t.Research).HasColumnName("Research");

            // Relationships
            this.HasRequired(t => t.Day)
                .WithMany(t => t.ProjectWorks)
                .HasForeignKey(d => new { d.EmployeeEmail, d.Date });
            this.HasRequired(t => t.Employee)
                .WithMany(t => t.ProjectWorks)
                .HasForeignKey(d => d.EmployeeEmail);
            this.HasRequired(t => t.Project1)
                .WithMany(t => t.ProjectWorks)
                .HasForeignKey(d => d.Project);

        }
    }
}
