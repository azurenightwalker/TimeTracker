using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Contracts;
using TimesheetData.Models;
using System.Web.Script.Serialization;

namespace SelfHostedRESTService
{
    public class Service :IService
    {
        public List<data> GetProjects()
        {
            var tdb = new XLabTimesheetsContext();

            return tdb.Projects.Where(a => a.Active).Select(a => new data{
                   Name = a.Name,
                   HasDev = a.HasDev,
                   HasResearch = a.HasResearch,
                   HasSupport = a.HasSupport
            }).ToList();
        }

        public string PutHours(string hours)
        {
            return "Calling Post for you " + hours; 
        }
    }
}
