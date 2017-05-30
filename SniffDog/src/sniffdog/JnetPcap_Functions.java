/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffdog;

import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

/**
 *
 * @author Rishabh
 */
public class JnetPcap_Functions {

    private List<PcapIf> dev = new ArrayList<>();
    private PcapIf device;
    private StringBuilder err = new StringBuilder();
    private String filename;

    public PcapIf getdevice() {
        return device;
    }

    public StringBuilder geterr() {
        return err;
    }

    public List<PcapIf> listNetworkInterfaces() {
        int r = Pcap.findAllDevs(dev, err);
        //System.out.println(Pcap.OK);

        if (r == Pcap.NOT_OK || dev.isEmpty()) {
            System.out.println("Can't read list of devices, error is " + err
                    .toString());
            return null;

        } else {
            for (PcapIf devices : dev) {
                System.out.println(devices);
            }
            return dev;
        }
    }

    public void set(int i) {
        if (i < 0 || i > dev.size() - 1) {
            System.out.println("Error in connecting to device");
        }
        device = dev.get(i);

    }

    public void setfilename(String s) {
        this.filename = s;
    }

    public String getfilename() {
        return this.filename;
    }
}
