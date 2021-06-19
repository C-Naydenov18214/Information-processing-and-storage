using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Controllers.HelpClasses
{
    public class OneToMany<K,L> 
    {
        public K KeyName { get; }
        public List<L> Elements { get; }

        public OneToMany(K k, L listElem)
        {
            KeyName = k;
            Elements = new List<L>();
            Elements.Add(listElem);
        }


    }
}
