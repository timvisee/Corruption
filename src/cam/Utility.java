package cam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.bukkit.Location;

public abstract class Utility {
	public static java.util.Random random = new java.util.Random();
	
	public static int Random(int min, int max) {
		int result = (int) (random.nextDouble() * (max - min + 1) + min);
		return result;
	}
	
	public static boolean IsNear(Location first, Location second, int minDistance, int maxDistance) {
		//TODO Square before circle.
		double relX = first.getX() - second.getX();
		double relY = first.getY() - second.getY();
		double relZ = first.getZ() - second.getZ();
		double dist = relX * relX + relY * relY + relZ * relZ;
		
		if (dist < maxDistance * maxDistance && dist > minDistance * minDistance)
			return true;
		
		return false;
	}
	
	private static class ValueComparatorAsc<K, V extends Comparable<? super V>> implements Comparator<Entry<K, V>> {
		@Override
		public int compare(Entry<K, V> e1, Entry<K, V> e2) {
			int res = e1.getValue().compareTo(e2.getValue());
			return res != 0 ? res : 1;
		}
	}
	
	private static class ValueComparatorDesc<K, V extends Comparable<? super V>> implements Comparator<Entry<K, V>> {
		@Override
		public int compare(Entry<K, V> e1, Entry<K, V> e2) {
			int res = e2.getValue().compareTo(e1.getValue());
			return res != 0 ? res : 1;
		}
	}
	
	public static <K, V extends Comparable<? super V>> SortedSet<Entry<K, V>> SortEntriesByValues(Map<K, V> map, boolean ascendant) {
		SortedSet<Map.Entry<K, V>> sortedEntries;
		if (ascendant)
			sortedEntries = new TreeSet<Entry<K, V>>(new ValueComparatorAsc<K, V>());
		else
			sortedEntries = new TreeSet<Entry<K, V>>(new ValueComparatorDesc<K, V>());
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
	
	public static void StreamToFile(InputStream resource, File file) throws Exception {
		OutputStream outputStream = new FileOutputStream(file);
		
		Copy(resource, outputStream);
	}
	
	public static void FileToFile(File source, File dest) throws Exception {
		InputStream inputStream = new FileInputStream(source);
		OutputStream outputStream = new FileOutputStream(dest);
		
		Copy(inputStream, outputStream);
	}
	
	private static void Copy(InputStream inputStream, OutputStream outputStream) throws Exception {
		int read = 0;
		byte[] bytes = new byte[1024];
		
		while ((read = inputStream.read(bytes)) != -1)
			outputStream.write(bytes, 0, read);
		
		inputStream.close();
		outputStream.close();
	}
}
