using System;
using System.Collections.Generic;

namespace TimesheetData.Models
{
    public partial class Employee
    {
        public Employee()
        {
            this.Days = new List<Day>();
            this.ProjectWorks = new List<ProjectWork>();
        }

        public string Email { get; set; }
        public string Name { get; set; }
        public bool FullTime { get; set; }
        public virtual ICollection<Day> Days { get; set; }
        public virtual ICollection<ProjectWork> ProjectWorks { get; set; }
    }
}
