/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffdog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JScanner;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;

/**
 *
 * @author Rishabh
 */
public class Pcap_File_Processing extends Thread {

    public static PcapPacket[] p = new PcapPacket[1000000];
    private static int number = 1;
    UI_Processing up;
    String addr = "C:\\Users\\Rishabh\\Downloads\\NetworkAnal\\";
JnetPcap_Functions jpf;
    public Pcap_File_Processing(UI_Processing up,JnetPcap_Functions jpf) {
        this.up = up;
        this.jpf=jpf;
        addr=addr+User_Interface.filename+".pcap";
    }
    //static DefaultTableModel dtm;

    public void run() {
while(User_Interface.stop==false){
        Pcap pcap
                = Pcap.openOffline(addr, new StringBuilder());

        PcapPacketHandler<String> jpacketHandler = new handler_UIDisplay();
        JScanner.getThreadLocal().setFrameNumber(0);  
        pcap.loop(-1, jpacketHandler, "jNetPcap rocks!");

        pcap.close();
}
    }

    private class handler_UIDisplay implements PcapPacketHandler<String> {

        int num = 0;

        @Override
        public void nextPacket(final PcapPacket packet, String user) {
            num++;
            if (num <= number) {
            } else {

                Ip4 ip = new Ip4();

                {

                    if (packet.hasHeader(ip)) {
                        Date date=new Date(packet.getCaptureHeader().timestampInMillis());
                        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss:SSS");
                        up.addRow(new Object[]{number,sdf.format(date) ,FormatUtils.ip(ip.source()), FormatUtils.ip(ip.destination())});
                        p[number++] = packet;
                    }
                }
            }

        }
    }
}
