package auto;

import javax.swing.JOptionPane;

public class MachineSz {

    // Machine Size
    public String diecutter = "Elite";
    public String gluer = "Bobst";
    public String desStyle = "";
    public double s1wayNet = 0;
    public double n2wayNet = 0;
    public double s1wayGross = 0;
    public double n2wayGross = 0;
    public boolean stdFluteFeed = true;
    public double l1 = 0; // length panel 1 main
    public double w2 = 0; //
    public double l3 = 0;
    public double w4 = 0;
    public double flange = 0;   
    public double topFlap = 0;
    public double btmFlap = 0;
    public double pnlDepth = 0; // Panel Depth
    
    
    public boolean checkLimits() {
    /*  Check the Plant limits to see if we can make this Pack        
        */        
        boolean rtnVal = true;
        
        eliteOK();
        
        bobstVgOK();
        
        
        return rtnVal;
    }
    
public boolean eliteOK() {
    boolean rtnVal = true;
    
    String msg = "";
    double trim1s = 35;
    double trim2n = 30; 
    double maxFeed = 1200 - trim1s; // change from 1100
    double maxnCrsFeed = 1620 - trim2n;
    double minFeed = 450 - trim1s; 
    double minCrsFeed = 650 - trim2n;
    
    if(s1wayNet > maxFeed) {
      msg = msg + "THROUGH MC SIZE TOO BIG by " + Double.toString(s1wayNet - maxFeed) + "\n";
    }
     if(n2wayNet > maxnCrsFeed) {
      msg = msg + "CROSS MC SIZE TOO BIG by " + Double.toString(n2wayNet - maxnCrsFeed) + "\n";
    }   
    if(s1wayNet < minFeed) {
      msg = msg + "THROUGH MC SIZE TOO SMALL by " + Double.toString(s1wayNet - minFeed) + "\n";
    }
     if(n2wayNet < minCrsFeed) {
      msg = msg + "CROSS MC SIZE TOO SMALL by " + Double.toString(n2wayNet - minCrsFeed) + "\n";
    }  
    
    if (msg.length() > 1) { 
        JOptionPane.showMessageDialog(null, msg, "ELITE - Plant Limits", JOptionPane.WARNING_MESSAGE);
    }     
    return rtnVal;
} // eliteOK


public boolean topraOK() {
    boolean rtnVal = true;
    
    String msg = "";
    double trim1s = 0; // ??????????
    double trim2n = 20; 
    double maxFeed = 1200 - trim1s; // change from 1100
    double maxnCrsFeed = 2200 - trim2n;
    double minFeed = 300 - trim1s; 
    double minCrsFeed = 600 - trim2n;
    
    if(s1wayNet > maxFeed) {
      msg = msg + "THROUGH MC SIZE TOO BIG by " + Double.toString(s1wayNet - maxFeed) + "\n";
    }
     if(n2wayNet > maxnCrsFeed) {
      msg = msg + "CROSS MC SIZE TOO BIG by " + Double.toString(n2wayNet - maxnCrsFeed) + "\n";
    }   
    if(s1wayNet < minFeed) {
      msg = msg + "THROUGH MC SIZE TOO SMALL by " + Double.toString(s1wayNet - minFeed) + "\n";
    }
    if(n2wayNet < minCrsFeed) {
      msg = msg + "CROSS MC SIZE TOO SMALL by " + Double.toString(n2wayNet - minCrsFeed) + "\n";
    } 
     
     
    if (msg.length() > 1) { 
        JOptionPane.showMessageDialog(null, msg, "TOPRA - Plant Limits", JOptionPane.WARNING_MESSAGE);
    } 
     return rtnVal;
} // topraOK


public boolean bobstVgOK() {
    boolean rtnVal = true;
    
    String msg = "";
    double maxFeed = 1100; 
    double maxCrsFeed = 1700;
    double minFeed = 60; 
    double minCrsFeed = 296;
    double minLWratio = 1;
    double maxLWratio = 6.375;
    // 1st & 2nd way Sizes
    if(s1wayNet > maxFeed) {
      msg = msg + "THROUGH MC SIZE TOO BIG by " + Double.toString(s1wayNet - maxFeed) + "\n";
    }
    if(n2wayNet > maxCrsFeed) {
      msg = msg + "CROSS MC SIZE TOO BIG by " + Double.toString(n2wayNet - maxCrsFeed) + "\n";
    }   
    if(s1wayNet < minFeed) {
      msg = msg + "THROUGH MC SIZE TOO SMALL by " + Double.toString(s1wayNet - minFeed) + "\n";
    }
     if(n2wayNet < minCrsFeed) {
      msg = msg + "CROSS MC SIZE TOO SMALL by " + Double.toString(n2wayNet - minCrsFeed) + "\n";
    }
    // Panel Sizes 
    String LWmsg = "";
    if(l1 + w2 > 835) {
      LWmsg = LWmsg + "Length+Width(P1+P2) TOO BIG by " + Double.toString(l1 + w2 - 835) + "\n";
    }
    if(l1 + w2 < 145) {
      LWmsg = LWmsg + "Length+Width(P1+P2) TOO SMALL by " + Double.toString(l1 + w2 - 145) + "\n";
    }
    if(l1 / w2 > maxLWratio) {
      LWmsg = LWmsg + "Length:Width Ratio(P1:P2) TOO BIG by " + Double.toString((l1 / w2) - maxLWratio) + "\n";
    }
    if(l1 / w2 < minLWratio) {
      LWmsg = LWmsg + "Length:Width Ratio(P1:P2) TOO SMALL by " + Double.toString((l1 / w2) - minLWratio) + "\n";
    }
    // Length Panel
    if(l1 < 72.5) {
      LWmsg = LWmsg + "Length(P1) TOO SMALL Crashlock by " + Double.toString(l1 - 72.5) + "\n";
    } else if(l1 < 115) {
      LWmsg = LWmsg + "**Length(P1) Crashlock *MAY BE* TOO SMALL by " + Double.toString(l1 - 115) + "**\n";
    } else if(l1 > 716) {
      LWmsg = LWmsg + "**Length(P1) 0713 Alt Panel Arrange TOO BIG by " + Double.toString(l1 - 780.5) + "\n";
    } else if(l1 > 780.5) {
      LWmsg = LWmsg + "Length(P1) 0713 TOO BIG by " + Double.toString(l1 - 716) + "**\n";
    }    
    
    // Width Panel
    if(w4 > 417.5) {
      LWmsg = LWmsg + "Width(P4) TOO BIG by " + Double.toString(w4 - 417.5) + "\n";
    } else if(w4 < 72.5) {
      LWmsg = LWmsg + "Width(P4) Crashlock *MAY BE* TOO SMALL by " + Double.toString(w4 - 72.5) + "\n";
    } else if(w4 < 30) {
      LWmsg = LWmsg + "Width(P4) Crashlock TOO SMALL by " + Double.toString(w4 - 30) + "\n";
    } else if(w4 < 27.5) {
      LWmsg = LWmsg + "Width(P4) 0211/0201 TOO SMALL by " + Double.toString(w4 - 27.5) + "\n";
    }
    msg = msg + LWmsg; // Add Panel Mesages 
    // flange
    if(flange > 30) {
      msg = msg + "FLANGE TOO BIG by " + Double.toString(flange - 30) + "\n";
    }
    if(flange < 6) {
      msg = msg + "FLANGE TOO SMALL by " + Double.toString(flange - 6) + "\n";
    }
    // Depth - No actual limits given 
    if(pnlDepth > 1100) {
      msg = msg + "Depth TOO BIG by " + Double.toString(pnlDepth - 1100) + "\n";
    }
     if(pnlDepth < 10) {
      msg = msg + "Depth TOO SMALL by " + Double.toString(pnlDepth - 10) + "\n";
    }
    
    /***** 4 POINT GLUED 0451/0304 DESIGNS ************************************************************************************/ 
    if (flange == 0) {
        if(s1wayNet > 1100) {
          msg = msg + "THROUGH MC SIZE TOO BIG by " + Double.toString(s1wayNet - 1100) + "\n";
        }
        if(n2wayNet > 1500) {
          msg = msg + "CROSS MC SIZE 0451/0304 TOO BIG by " + Double.toString(n2wayNet - 1500) + "\n";
        }   
        if(s1wayNet < 130) {
          msg = msg + "THROUGH MC SIZE TOO SMALL by " + Double.toString(s1wayNet - 130) + "\n";
        }
         if(n2wayNet < 205) {
          msg = msg + "CROSS MC SIZE 0451/0304 TOO SMALL by " + Double.toString(n2wayNet - 205) + "\n";
        }
        if(pnlDepth > 200) {
          msg = msg + "Depth TOO BIG by " + Double.toString(pnlDepth - 200) + "\n";
        }
        if(pnlDepth < 30) {
          msg = msg + "Depth TOO SMALL by " + Double.toString(pnlDepth - 30) + "\n";
        }
        double panelSz = n2wayNet - (pnlDepth * 2); // Panel size accross the machine
        if(panelSz > 1200) {
          msg = msg + "CROSS MC Panel 0451/0304 TOO BIG by " + Double.toString(panelSz - 1200) + "\n";
        }
        if(panelSz < 145) {
          msg = msg + "CROSS MC Panel 0451/0304 TOO SMALL by " + Double.toString(panelSz - 145) + "\n";
        }   
    }
    
    if (msg.length() > 1) { 
        JOptionPane.showMessageDialog(null, msg, "Plant Limits - BOBST VARIABLE GLUER", JOptionPane.WARNING_MESSAGE);
    } 
     return rtnVal;
} // bobstVgOK


public boolean pakmet() {
//-*********************
// Pakmet gluer
// tg - 11/7/95
//-*********************
boolean rtnVal = true;
  String msg = "";

    if(s1wayNet < 152) {
      msg = msg + "Check LIMITS - PAKMET 1st WAY TOO SMALL by " + Double.toString(s1wayNet - 152) + "\n";
    }
/*    
    men := 'Check LIMITS - PAKMET' + #13#10 + '1st WAY TOO BIG by ' + IntToStr(s1brd - 2540);
    If s1brd > 2540 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + '2nd WAY TOO SMALL by ' + IntToStr(n2brd - 322);
    If n2brd < 322 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + '2nd WAY TOO BIG by ' + IntToStr(s1brd - 2544);
    If n2brd > 2544 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    Val(spec.EditLENGTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'LENGTH TOO SMALL by ' + IntToStr(temp - 76);
    If temp < 76 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    Val(spec.EditLENGTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'LENGTH TOO BIG by ' + IntToStr(Temp - 615);
    If Temp > 615 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    {Val(spec.EditWIDTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'CHECK FLAP SIZE? TOO BIG by ' + IntToStr((temp / 2 - 355)) + '?';
    If (temp / 2) > 355 Then
      MessageDlg(men, mtInformation, [mbOk], 0);}

    men := 'Check LIMITS - PAKMET' + #13#10 + 'WIDTH TOO SMALL by ' + IntToStr(temp - 76);
    If temp < 76 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + 'WIDTH TOO BIG by ' + IntToStr(temp - 615);
    If temp > 615 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    Val(spec.EditDEPTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'DEPTH TOO SMALL by ' + IntToStr(temp - 50);
    If temp < 50 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + 'DEPTH TOO BIG by ' + IntToStr(temp - 1828);
    If temp > 1828 Then
      MessageDlg(men, mtInformation, [mbOk], 0);
*/
    if (msg.length() > 1) { 
        JOptionPane.showMessageDialog(null, msg, "Plant Limits - GLUER", JOptionPane.WARNING_MESSAGE);
    }
    return rtnVal;
} // pakmet


    
} // MachineSz
