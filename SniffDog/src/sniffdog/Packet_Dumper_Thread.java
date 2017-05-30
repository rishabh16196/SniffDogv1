/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffDog;

import java.nio.ByteBuffer;
import javax.swing.SwingWorker;
import org.jnetpcap.ByteBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;

/**
 *
 * @author Rishabh
 */
public class Packet_Dumper_Thread extends Thread {

    static Pcap pcap;
    sniffdog.JnetPcap_Functions jpf;
 ByteBufferHandler<PcapDumper> handler1;
 PcapDumper dumper;
    public Packet_Dumper_Thread(sniffdog.JnetPcap_Functions jpf) {
        this.jpf = jpf;
        init();
    }
private void init(){
int snaplen = 64 * 1064;
        int flags = Pcap.MODE_PROMISCUOUS;
        int timeout = 10 * 1000;
        String file = "C:\\Users\\Rishabh\\Downloads\\NetworkAnal\\" + jpf.getfilename() + ".pcap";

        PcapBpfProgram prog = new PcapBpfProgram();

        pcap = Pcap.openLive(jpf.getdevice().getName(), snaplen, flags, timeout, jpf.geterr());

       /* if (User_Interface.jToggleButton1.isSelected()) {
            String filter = User_Interface.jTextField3.getText();
            pcap.compile(prog, filter, 0, 0xFFFFFF00);
            pcap.setFilter(prog);
        }*/
        dumper = pcap.dumpOpen(file);

        handler1 = new ByteBufferHandler<PcapDumper>() {
            @Override
            public void nextPacket(PcapHeader header, ByteBuffer buffer, PcapDumper user) {

                dumper.dump(header, buffer);

            }
        };

}
    @Override
    public void run() {

        while(!interrupted())

        pcap.loop(1, handler1, dumper);
        

    }

}
