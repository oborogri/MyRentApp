package utils;

import java.util.Collections;
import java.util.Comparator;
import models.Residence;

public class DescendingRentComparator implements Comparator<Residence> {

	@Override
	public int compare(Residence a, Residence b) {
		return (b.rent - a.rent);
	}
}
