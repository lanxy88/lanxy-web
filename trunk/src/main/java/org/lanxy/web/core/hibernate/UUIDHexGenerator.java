package org.lanxy.web.core.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * .
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 13-7-16 下午10:57
 */
public final class UUIDHexGenerator implements IdentifierGenerator {

    private static final Logger LOG = Logger.getLogger(UUIDHexGenerator.class.getName());
    private static final AtomicInteger COUNTER;
    private static final int JVM;
    private static final int IP;

    static {
        COUNTER = new AtomicInteger(0);
        JVM = (int) (System.currentTimeMillis() >>> 8);
        IP = toInt(getInetAddress());
    }

    private static byte[] getInetAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || ni.isPointToPoint() || !ni.isUp()) {
                    continue;
                }
                String name = ni.getDisplayName().toLowerCase();
                if (name.contains("convnet") || name.contains("vmnet")) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    byte[] addr = addresses.nextElement().getAddress();
                    if (addr.length == 4) {
                        return addr;
                    }
                }
            }
        } catch (Exception e) {
            LOG.warning("Error to get ip address");
        }
        return null;
    }

    private static int toInt(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    private static short getCount() {
        COUNTER.compareAndSet(Short.MAX_VALUE, 0);
        return (short) COUNTER.incrementAndGet();
    }

    private static String format(int intValue) {
        return StringUtils.leftPad(Integer.toHexString(intValue), 8, "0");
    }

    private static String format(short shortValue) {
        return StringUtils.leftPad(Integer.toHexString(shortValue), 4, "0");
    }

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
        return generate();
    }

    public static String generate() {
        long now = System.currentTimeMillis();
        short hiTime = (short) (now >>> 32);
        int loTime = (int) now;
        return format(hiTime) + format(loTime) + format(IP) + format(JVM) + format(getCount());
    }
}
