package auto;

/**
 * @author Tim Gathercole 15/01/2017
 * https://stackoverflow.com/questions/24771828/algorithm-for-creating-rounded-corners-in-a-polygon
 */
public class FilletLines2 {

    public double P1X = 0; // P1X  // Line 1 X
    public double P1Y = 0; // P1Y  // Line 1 Y
    public double PX = 0;  // PX   // Lines Meet Point X          incLineMeetX
    public double PY = 0;  // PY   // Lines Meet Point Y          incLineMeetY
    public double P2X = 0; // P2X  // Line 2 X
    public double P2Y = 0; // P2Y  // Line 2 Y
    public double radius = 0;
    /**
     * General case Fillet that works?
     */
    
    
    public void filletLines() {
        
//        double P1X = 2.4909, P1Y = 2.4971; //, pa2x = 4.513, pa2y = 6.4901;
//        double PX = 6.9831, PY = 6.4773, P2X = 14.0477, P2Y = 1.9979;
//        // Answer = 80.4837 degrees
//        double radius = 1.2;
        
        double angle = Math.atan2(PY - P1Y, PX - P1X) - Math.atan2(PY - P2Y, PX - P2X);
        System.out.print("Angle betwwen Lines = " + Math.toDegrees(angle) + " Degrees\n"); // 
        
        //3. Get the length of segment between angular point and the points of intersection with the circle.
        double segment = radius / (Math.tan(angle / 2)); // Distance from Line meet point to the hit point of the Radius
        double PC1 = segment, PC2 = segment;
        System.out.print("Segment= " + segment + "\n");
    
        //4. Here you need to check the length of segment and the minimal length from PP1 and PP2:
        //Length of PP1:
        double PP1 = Math.sqrt(Math.pow((PX - P1X), 2) + Math.pow((PY - P1Y), 2));
    //Length of PP2:
        double PP2 = Math.sqrt(Math.pow((PX - P2X), 2) + Math.pow((PY - P2Y), 2));

//If segment > PP1 or segment > PP2 then you need to decrease the radius:
/* min = Min(PP1, PP2) (for polygon is better to divide this value by 2)
segment > min ?
    segment = min
    radius = segment * |tan(angle / 2)|
*/        
         // 5. Get the length of PO:
        double PO = Math.sqrt(Math.pow(radius, 2) + Math.pow(segment, 2));
        System.out.print("PO= " + PO + "\n"); // Distance down from the Meet point to the Centre of the Radius Arc

        // 6. Get the C1X and C1Y by the proportion between the coordinates of the vector, length of vector and the length of the segment:
        // Proportion:
        //(PX - C1X) / (PX - P1X) = PC1 / PP1
        //So:
        double C1X = PX - (PX - P1X) * PC1 / PP1;
        // The same for C1Y:
        double C1Y = PY - (PY - P1Y) * PC1 / PP1;

        // 7. Get the C2X and C2Y by the same way:
        double C2X = PX - (PX - P2X) * PC2 / PP2;
        double C2Y = PY - (PY - P2Y) * PC2 / PP2;

        // 8. Now you can use the addition of vectors PC1 and PC2 to find the centre of circle by the same way by proportion:
        //(PX - OX) / (PX - CX) = PO / PC
        //(PY - OY) / (PY - CY) = PO / PC
        //Here:
        double CX = C1X + C2X - PX;
        double CY = C1Y + C2Y - PY;
        double PC = Math.sqrt(Math.pow((PX - CX), 2) + Math.pow((PY - CY), 2));

        // Let:
        double dx = PX - CX; // = PX * 2 - C1X - C2X;
        double dy = PY - CY; // = PY * 2 - C1Y - C2Y;

        // So:
        PC = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double OX = PX - dx * PO / PC;
        double OY = PY - dy * PO / PC;
        System.out.print("CX(7.103)= " + OX + " ,CY(4.9804)=" + OY + "\n");

    // 9. Here you can draw an arc. For this you need to get start angle and end angle of arc:
        double startAngle = Math.atan((C1Y - OY) / (C1X - OX));
        double endAngle = Math.atan((C2Y - OY) / (C2X - OX));
        double sweepAngle = 0;
        
        startAngle = 180 + Math.toDegrees(startAngle); // ???
        // If sweepAngle < 0 then swap startAngle and endAngle, and invert sweepAngle:
        if (sweepAngle < 0) {     
            sweepAngle =- sweepAngle;
            startAngle = endAngle;
        } // if
        // Check if sweepAngle > 180 degrees:
        if (sweepAngle > 180) {     
            sweepAngle = 180 - sweepAngle;
        } // if
        
System.out.print("startAngle(58)= " + startAngle + " ,endAngle(132)=" + Math.toDegrees(endAngle) + "\n");        
        
    }   
}
