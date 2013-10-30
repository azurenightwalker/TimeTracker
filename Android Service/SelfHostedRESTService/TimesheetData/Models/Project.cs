using System;
using System.Collections.Generic;

namespace TimesheetData.Models
{
    public partial class Project
    {
        public Project()
        {
            this.ProjectWorks = new List<ProjectWork>();
        }

        public string Name { get; set; }
        public bool HasDev { get; set; }
        public bool HasSupport { get; set; }
        public bool HasResearch { get; set; }
        public bool Active { get; set; }
        public virtual ICollection<ProjectWork> ProjectWorks { get; set; }
    }
}
