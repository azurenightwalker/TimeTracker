using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Contracts
{
    public class data
    {
        //public data(string name, bool hasDev, bool hasSupport, bool hasResearch)
        //{
        //    Name = name;
        //    HasDev = hasDev;
        //    HasSupport = hasSupport;
        //    HasResearch = hasResearch;
        //}

        public data() { }

        public string Name { get; set; }
        public bool HasDev { get; set; }
        public bool HasSupport { get; set; }
        public bool HasResearch { get; set; }

    }
}
