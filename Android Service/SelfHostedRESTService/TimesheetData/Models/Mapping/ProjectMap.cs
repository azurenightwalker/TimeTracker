using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;

namespace TimesheetData.Models.Mapping
{
    public class ProjectMap : EntityTypeConfiguration<Project>
    {
        public ProjectMap()
        {
            // Primary Key
            this.HasKey(t => t.Name);

            // Properties
            this.Property(t => t.Name)
                .IsRequired()
                .HasMaxLength(50);

            // Table & Column Mappings
            this.ToTable("Projects");
            this.Property(t => t.Name).HasColumnName("Name");
            this.Property(t => t.HasDev).HasColumnName("HasDev");
            this.Property(t => t.HasSupport).HasColumnName("HasSupport");
            this.Property(t => t.HasResearch).HasColumnName("HasResearch");
            this.Property(t => t.HasSales).HasColumnName("HasSales");
            this.Property(t => t.Active).HasColumnName("Active");
        }
    }
}
