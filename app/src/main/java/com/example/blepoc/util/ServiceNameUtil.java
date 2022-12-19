package com.example.blepoc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServiceNameUtil {
    private static Map<String, String> serviceName = new HashMap<>();

    private static Map<String, String> getMapNames() {
        if (!serviceName.isEmpty())
            return serviceName;
        serviceName.put("Generic Access", "1800");
        serviceName.put("Alert Notification Service", "1811");
        serviceName.put("Automation IO", "1815");
        serviceName.put("Battery Service", "180F");
        serviceName.put("Blood Pressure", "1810");
        serviceName.put("Body Composition", "181B");
        serviceName.put("Bond Management Service", "181E");
        serviceName.put("Continuous Glucose Monitoring", "181F");
        serviceName.put("Current Time Service", "1805");
        serviceName.put("Cycling Power", "1818");
        serviceName.put("Cycling Speed and Cadence", "1816");
        serviceName.put("Device Information", "180A");
        serviceName.put("Environmental Sensing", "181A");
        serviceName.put("Fitness Machine", "1826");
        serviceName.put("Generic Attribute", "1801");
        serviceName.put("Glucose", "1808");
        serviceName.put("Health Thermometer", "1809");
        serviceName.put("Heart Rate", "180D");
        serviceName.put("HTTP Proxy", "1823");
        serviceName.put("Human Interface Device", "1812");
        serviceName.put("Immediate Alert", "1802");
        serviceName.put("Indoor Positioning", "1821");
        serviceName.put("Insulin Delivery", "183A");
        serviceName.put("Internet Protocol Support Service", "1820");
        serviceName.put("Link Loss", "1803");
        serviceName.put("Location and Navigation", "1819");
        serviceName.put("Mesh Provisioning Service", "1827");
        serviceName.put("Mesh Proxy Service", "1828");
        serviceName.put("Next DST Change Service", "1807");
        serviceName.put("Object Transfer Service", "1825");
        serviceName.put("Phone Alert Status Service", "180E");
        serviceName.put("Pulse Oximeter Service", "1822");
        serviceName.put("Reconnection Configuration", "1829");
        serviceName.put("Reference Time Update Service", "1806");
        serviceName.put("Running Speed and Cadence", "1814");
        serviceName.put("Scan Parameters", "1813");
        serviceName.put("Transport Discovery", "1824");
        serviceName.put("Tx Power", "1804");
        serviceName.put("User Data", "181C");
        serviceName.put("Weight Scale", "181D");
        serviceName.put("Binary Sensor", "183B");
        serviceName.put("Emergency Configuration", "183C");
        serviceName.put("Physical Activity Monitor", "183E");
        serviceName.put("Audio Input Control", "1843");
        serviceName.put("Volume Control", "1844");
        serviceName.put("Volume Offset Control", "1845");
        serviceName.put("Coordinated Set Identification", "1846");
        serviceName.put("Device Time", "1847");
        serviceName.put("Media Control", "1848");
        serviceName.put("Generic Media Control", "1849");
        serviceName.put("Constant Tone Extension", "184A");
        serviceName.put("Telephone Bearer", "184B");
        serviceName.put("Generic Telephone Bearer", "184C");
        serviceName.put("Microphone Control", "184D");
        serviceName.put("Audio Stream Control", "184E");
        serviceName.put("Broadcast Audio Scan", "184F");
        serviceName.put("Published Audio Capabilities", "1850");
        serviceName.put("Basic Audio Announcement", "1851");
        serviceName.put("Broadcast Audio Announcement", "1852");
        serviceName.put("Common Audio", "1853");
        serviceName.put("Hearing Access", "1854");
        serviceName.put("TMAS", "1855");
        serviceName.put("Phillips Hue Light Control Service", "932C32BD-0000-47A2-835A-A8D455B859DD");
        serviceName.put("Phillips Hue Light Information Service", "0000180A-0000-1000-8000-00805F9B34FB");
        serviceName.put("Phillips Hue Light Update Service", "B8843ADD-0000-4AA1-8794-C3F462030BDA");
        serviceName.put("Apple Notification Center Service", "7905F431-B5CE-4E99-A40F-4B1E122D00D0");
        serviceName.put("Apple Media Service", "89D3502B-0F36-433A-8EF4-C502AD55F8DC");
        serviceName.put("Apple Reserved Service", "7DFC6000-7D1C-4951-86AA-8D9728F8D66C");
        serviceName.put("Apple Reserved Service", "7DFC7000-7D1C-4951-86AA-8D9728F8D66C");
        serviceName.put("Apple Reserved Service", "7DFC8000-7D1C-4951-86AA-8D9728F8D66C");
        serviceName.put("Apple Reserved Service", "7DFC9000-7D1C-4951-86AA-8D9728F8D66C");
        serviceName.put("micro:bit Accelerometer Service", "E95D0753-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit Magnetometer Service", "E95DF2D8-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit Button Service", "E95D9882-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit IO Pin Service", "E95D127B-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit LED Service", "E95DD91D-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit Event Service", "E95D93AF-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit DFU Control Service", "E95D93B0-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("micro:bit Temperature Service", "E95D6100-251D-470A-A062-FA1922DFA9A8");
        serviceName.put("Thingy Configuration Service", "EF680100-9B35-4933-9B10-52FFA9740042");
        serviceName.put("Thingy Weather Station Service", "EF680200-9B35-4933-9B10-52FFA9740042");
        serviceName.put("Thingy UI Service", "EF680300-9B35-4933-9B10-52FFA9740042");
        serviceName.put("Thingy Motion Service", "EF680400-9B35-4933-9B10-52FFA9740042");
        serviceName.put("Thingy Sound Service", "EF680500-9B35-4933-9B10-52FFA9740042");
        serviceName.put("Nordic LED and Button Service", "00001523-1212-EFDE-1523-785FEABCD123");
        serviceName.put("Nordic UART Service", "6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
        serviceName.put("Eddystone", "FEAA");
        serviceName.put("Eddystone Configuration Service", "A3C87500-8ED3-4BDF-8A39-A01BEBEDE295");
        serviceName.put("Fast Pair Service", "FE2C");
        serviceName.put("Legacy DFU Service", "00001530-1212-EFDE-1523-785FEABCD123");
        serviceName.put("Secure DFU Service", "FE59");
        serviceName.put("Edge Impulse Remote Management Service", "E2A00001-EC31-4EC3-A97A-1C34D87E9878");
        serviceName.put("Exposure Notification Service", "FD6F");
        serviceName.put("SMP Service", "8D53DC1D-1DB7-4CD3-868B-8A527460AA84");
        serviceName.put("LEGO® Wireless Protocol v3 Hub Service", "00001623-1212-EFDE-1623-785FEABCD123");
        serviceName.put("LEGO® Wireless Protocol v3 Bootloader Service", "00001625-1212-EFDE-1623-785FEABCD123");
        serviceName.put("File Transfer Service by Adafruit", "FEBB");
        serviceName.put("Adafruit Temperature Service", "ADAF0100-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Accelerometer Service", "ADAF0200-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Light Service", "ADAF0300-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Gyroscope Service", "ADAF0400-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Magnetometer Service", "ADAF0500-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Button Service", "ADAF0600-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Humidity Service", "ADAF0700-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Barometric Service", "ADAF0800-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Addressable Service", "ADAF0900-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Color Service", "ADAF0A00-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Sound Service", "ADAF0B00-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Tone Service", "ADAF0C00-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Quaternion Service", "ADAF0D00-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Adafruit Proximity Service", "ADAF0E00-C332-42A8-93BD-25E905756CB8");
        serviceName.put("Texas Instruments Over-the-Air Download (OAD) Servi", "F000FFC0-0451-4000-B000-000000000000");
        serviceName.put("Helium Hotspot Custom Service", "0FDA92B2-44A2-4AF2-84F5-FA682BAA2B8D");
        serviceName.put("Memfault Diagnostic Service", "54220000-F6A5-4007-A371-722F4EBD8436");

        return serviceName;
    }


    public static String getName(UUID uuidService) {
        String serviceId = uuidService.toString().substring(4, 8);
        if (getMapNames().containsKey(serviceId)) {
            return getMapNames().get(serviceId);
        }
        return "Not configured";
    }

}
