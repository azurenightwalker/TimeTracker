using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using TimesheetData.Models;
using TimesheetService.Data;

namespace TimesheetService.Controllers
{
    public class ProjectsController : ApiController
    {
        // GET api/projects
        public IEnumerable<ProjectDescription> Get()
        {
            using (TimesheetsContext tdb = new TimesheetsContext())
            {
                return tdb.Projects.Where(a => a.Active).Select(a => new ProjectDescription
                {
                    Name = a.Name,
                    HasDev = a.HasDev,
                    HasResearch = a.HasResearch,
                    HasSupport = a.HasSupport,
                    HasSales = a.HasSales
                }).ToList();
            }
        }

        // GET api/projects/name
        public ProjectDescription Get(string name)
        {
            using (TimesheetsContext tdb = new TimesheetsContext())
            {
                var pro = tdb.Projects.Single(a => a.Name == name);
                return new ProjectDescription
                {
                    Name = pro.Name,
                    HasDev = pro.HasDev,
                    HasResearch = pro.HasResearch,
                    HasSupport = pro.HasSupport,
                    HasSales = pro.HasSales
                };
            }
        }
    }
}