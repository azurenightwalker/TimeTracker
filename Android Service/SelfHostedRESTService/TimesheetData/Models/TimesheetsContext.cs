using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using TimesheetData.Models.Mapping;

namespace TimesheetData.Models
{
    public partial class TimesheetsContext : DbContext
    {
        static TimesheetsContext()
        {
            Database.SetInitializer<TimesheetsContext>(null);
        }

        public TimesheetsContext()
            : base("Name=TimesheetsContext")
        {
        }

        public DbSet<Day> Days { get; set; }
        public DbSet<Employee> Employees { get; set; }
        public DbSet<Project> Projects { get; set; }
        public DbSet<ProjectWork> ProjectWorks { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Configurations.Add(new DayMap());
            modelBuilder.Configurations.Add(new EmployeeMap());
            modelBuilder.Configurations.Add(new ProjectMap());
            modelBuilder.Configurations.Add(new ProjectWorkMap());
        }
    }
}
