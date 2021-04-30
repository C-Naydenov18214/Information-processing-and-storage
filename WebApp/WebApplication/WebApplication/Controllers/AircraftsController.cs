using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Controllers
{
    //[Route("api/[controller]")]
    //[ApiController]
    public class AircraftsController : ControllerBase
    {
        DemoContext db;

        public AircraftsController(DemoContext context)
        {
            db = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Aircraft>>> Get()
        {
            return await db.AircraftsData.ToListAsync();
        
        }


    }
}
