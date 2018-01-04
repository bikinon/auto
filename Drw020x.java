package auto;

public class Drw020x extends dxf12objects {

//    public String topStyle;
    public double l1 = 0; // length main
    public double w2 = 0; //
    public double l3 = 0;
    public double w4 = 0;
    public double flange = 0; 
    
    public double dotr = 0; 
    public double bofst = 0;
    public double slot = 0;
    
    public String CUT = "CUT";
    public String CREASE = "CREASE"; 
  
    
    
    public String drwFlaps(double flap, double inrFlap) {      
        double ac = 2; // Angle Cut-in?
        
      this.angFlapOtr(this.l1, flap, slot, ac, bofst, slot);
        this.relMove(slot + ac, -(flap + bofst)); //-inrFlap);
        
        this.ChamFlapInr(this.w2, 2, inrFlap, (flap / 3), 15);  
        
        if (this.Yaxis == -1) {
        //  this.relMove(0, bofst*2); // No idea why
        } else {
          this.relMove(0, -bofst*2); // No idea why
        }
        this.angFlapOtr(this.l3, flap, slot, ac, bofst, slot);
        
        this.absMove(this.l1 + this.w2 + this.l3, 0);
        if(this.Yaxis == 1) {
         this.relMove(0, this.dotr);
        }
      this.ChamFlapInrP4(this.w4, 2, inrFlap, (flap / 3), 15, 15 - (w2-w4) + 2);
      
      return dxf;
    } // drw020x
    
    

    /**
     *  
     * @param dis - Panel Distance
     * @param flp - Flap Height
     * @param slot - Slot (1/2)
     * @param ac  - Angle Cut
     * @param bof - Bend Offset
     * @param bof - Radius of Fillet
     */
     protected void angFlapOtr(double dis, double flp, double slot, double ac, double bof, double rad) {
   // *** Draw Angled, normally an OUTER with an offset in Depth of flap ***    
    double lbc = 0;
    double flatSide[] = {0,0,0,0};
          if (bof > 0) {
             FilletLines oTmp = new FilletLines();
             lbc = oTmp.findBendXonArc(bof, rad); 
             flatSide = oTmp.findFilletPoints(ac, flp + bof, rad);
             lbc = lbc + (slot - flatSide[3]);
          } else {  
             lbc = slot - rad;
          }
          relMove(lbc, bof);
          Line( dis - (lbc * 2), 0, "CREASE"); // CREASE Line
          relMove( -dis + lbc, -bof); // Re-set Origin Point

          FilletLines oFl = new FilletLines(); // Takes mainly Incremental line values
          oFl.absFlatXstr = this.xabs; // Start Point
          oFl.absFlatYstr = this.yabs;    
          oFl.incLineMeetX = slot; 
      oFl.incLineMeetY = 0; 
      oFl.incAngXend = ac; 
      oFl.incAngYend = flp + bof; 
          oFl.radius = rad;
          oFl.dir = "L"; // LEFT SIDE: X axis = 1
          oFl.Yaxis = this.Yaxis; // UP or DOWN
          dxf += oFl.fillet();
      
    this.relMove(slot + ac, flp + bof);

    Line(dis - (slot * 2) - (ac * 2), 0, "CUT"); // Top of Flap
    // this.absMove(this.xabs + slot + ac, this.yabs - (flp + bof));
          this.relMove(slot + ac, -(flp + bof)); // Outer edge, Bottom of slot to do the other side
    
          FilletLines oFl2 = new FilletLines();
          oFl2.absFlatXstr = this.xabs;
          oFl2.absFlatYstr = this.yabs;
          oFl2.incLineMeetX = slot; 
          oFl2.incLineMeetY = 0; 
          oFl2.incAngXend = -ac;
          oFl2.incAngYend = flp + bof;
          oFl2.radius = rad;
          oFl2.dir = "R"; // RIGHT SIDE: X axis = -1
          oFl2.Yaxis = this.Yaxis; // UP or DOWN
          dxf += oFl2.fillet();

          this.relMove(-(slot + ac), flp + bof);   
     
     } // angflapotr 

    
    /**
     * *** Draw Chamfered, Normally INNER flap ***
     * @param dis
     * @param slot - Slot size / 2
     * @param flp  - length of flap
     * @param adfcut - cut back on flap
     * @param aitop - angle size on top
     */
     protected void ChamFlapInr(double dis, double slot, double flp, double adfcut, double aitop) // (CONST vector start, endpnt_Xaxis, CONST Boolean YAxisUpwards,CONST LENGTH slot,flp,adfcut,aitop)
    {
      this.relMove(dis, 0);
      Line( -dis, 0, CREASE);

      if(this.Yaxis == -1) {
        this.arc2(this.xabs, this.yabs - slot, slot, 0, 90, slot, -slot, CUT);
      } else {  
        this.arc2(this.xabs, this.yabs + slot, slot, 270, 0, slot, slot, CUT);
      } 
      Line( 0, flp-adfcut-slot, CUT );
      Line(aitop, adfcut, CUT);
      Line(dis-(slot*2)-aitop*2 , 0, CUT);
      Line(aitop,-adfcut, CUT);
      Line(0, -flp+adfcut+slot, CUT);

      if(this.Yaxis == -1) {
        this.arc2(this.xabs + slot, this.yabs, slot, 90, 180, slot, slot, CUT);
      } else {
        this.arc2(this.xabs + slot, this.yabs, slot, 180, 270, slot, slot, CUT);
      }
    } // ChamFlapInr  
      

     /**
      *  
      * @param dis - Panel Size
      * @param slot - Slot width (1 side)
      * @param flp - Flap Length
      * @param adfcut - Verticle Angle (flp/3)
      * @param aitop - Cut in on top of flap R side
      * @param r4 - Cut in on top of flap L side
      */
     protected void  ChamFlapInrP4(double dis, double slot, double flp, double adfcut, double aitop, double r4) {
      //*** P4 Draw Chamfered, Normally INNER flap ***
      this.relMove(dis, 0);
      Line( -dis, 0, CREASE);
      
      if(this.Yaxis == -1) {
        this.arc2(this.xabs, this.yabs - slot, slot, 0, 90, slot, -slot, CUT);    
      } else {
        this.arc2(this.xabs, this.yabs + slot, slot, 270, 0, slot, slot, CUT);
      }

      Line( 0, flp-adfcut-slot, CUT );
      Line(aitop, adfcut, CUT);

      Line(dis-slot-aitop-r4 , 0, CUT);
      Line(r4,-adfcut, CUT);

      Line(0, -flp+adfcut, CUT);
      } // P4ChamFlapInr
    
    
  
} // drw02xx
