package com.zju.utils.lang;




import com.zju.utils.Lang;
import com.zju.utils.log.Log;
import com.zju.utils.log.Logs;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Object ID 24位通用ID生成器
 * 
 *
 * 
 */
public class OID {

	private static Log log = Logs.getLog();

	private static AtomicInteger _nextInc = new AtomicInteger(
			(new Random()).nextInt());

	private static final int _genmachine;
	static {
		try {
			int machinePiece;
			{
				try {
					StringBuilder sb = new StringBuilder();
					Enumeration<NetworkInterface> e = NetworkInterface
							.getNetworkInterfaces();
					while (e.hasMoreElements()) {
						NetworkInterface ni = e.nextElement();
						sb.append(ni.toString());
					}
					machinePiece = sb.toString().hashCode() << 16;
				} catch (Throwable e) {
					log.warn(e);
					machinePiece = (new Random().nextInt()) << 16;
				}
				log.debug("machine piece post: {}",
						Integer.toHexString(machinePiece));
			}

			final int processPiece;
			{
				int processId = new Random().nextInt();
				try {
					processId = java.lang.management.ManagementFactory
							.getRuntimeMXBean().getName().hashCode();
				} catch (Throwable t) {
				}

				ClassLoader loader = OID.class.getClassLoader();
				int loaderId = loader != null ? System.identityHashCode(loader)
						: 0;

				StringBuilder sb = new StringBuilder();
				sb.append(Integer.toHexString(processId));
				sb.append(Integer.toHexString(loaderId));
				processPiece = sb.toString().hashCode() & 0xFFFF;
				log.debug("process piece: {}",
						Integer.toHexString(processPiece));
			}

			_genmachine = machinePiece | processPiece;
			log.debug("machine : {}", Integer.toHexString(_genmachine));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int _time;

	private int _machine;

	private int _inc;

	public OID() {
		_time = (int) (System.currentTimeMillis() / 1000);
		_machine = _genmachine;
		_inc = _nextInc.getAndIncrement();
	}

	public byte[] toByteArray() {
		byte b[] = new byte[12];
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.putInt(_time);
		bb.putInt(_machine);
		bb.putInt(_inc);
		return b;
	}

	public String toString() {
		byte b[] = toByteArray();
		StringBuilder buf = new StringBuilder(24);
		for (int i = 0; i < b.length; i++) {
			int x = b[i] & 0xFF;
			String s = Integer.toHexString(x);
			if (s.length() == 1)
				buf.append("0");
			buf.append(s);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		System.out.println(new OID());
		final Set<String> idSet = Lang.newSet();
		System.out.println(Lang.timing(new Runnable() {
			public void run() {
				for (int i = 0; i < 1000000; i++) {
					String id = new OID().toString();
					if (idSet.contains(id)) {
						throw new RuntimeException();
					}
					idSet.add(id);
				}
			}
		}));
	}

}
