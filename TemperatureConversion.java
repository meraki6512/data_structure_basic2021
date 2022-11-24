package data;

public class TemperatureConversion {
	
	public static void main(String[] args) {
		final double TABLE_BEGIN = -50.0; // The table's first Celsius temperature
		final double TABLE_END = 50.0; // The table's final Celsius temperature
		final double TABLE_STEP = 10.0; // Increment between temperatures in table
		double celsius; // A Celsius temperature
		double fahrenheit; // The equivalent Fahrenheit temperature
		System.out.println("TEMPERATURE CONVERSION");
		System.out.println("----------------------");
		System.out.println("Celsius Fahrenheit");
		for (celsius = TABLE_BEGIN; celsius <= TABLE_END; celsius += TABLE_STEP) { // Each iteration prints one line of
																					// the conversion table.
			fahrenheit = celsiusToFahrenheit(celsius);
			System.out.printf("%6.2fC", celsius);
			System.out.printf("%14.2fF\n", fahrenheit);
		}
		System.out.println("----------------------");
	}

	/**
	 * Convert a temperature from Celsius degrees to Fahrenheit degrees.
	 **/
	public static double celsiusToFahrenheit(double c) {
		final double MINIMUM_CELCIUS = -273.16;
		if (c < MINIMUM_CELCIUS)
			throw new IllegalArgumentException("Argument " + c + " is too small.");
		return (9.0 / 5.0) * c + 32;
	}
}
