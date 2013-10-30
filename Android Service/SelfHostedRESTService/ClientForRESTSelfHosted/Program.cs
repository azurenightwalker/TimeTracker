using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.ServiceModel.Description;
using System.Text;
using System.Threading.Tasks;
using Contracts;

namespace ClientForRESTSelfHosted
{
    class Program
    {
        static void Main(string[] args)
        {
            ChannelFactory<IService> cf = new ChannelFactory<IService>(new WebHttpBinding(),
                "http://localhost:8000");
            cf.Endpoint.Behaviors.Add(new WebHttpBehavior());
            IService channel = cf.CreateChannel();
            Console.WriteLine(channel.GetProjects());
            Console.WriteLine(channel.PutHours("5"));
            Console.Read();
        }
    }
}
