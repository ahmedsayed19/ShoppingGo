#include <avr/io.h>
#include <inttypes.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdint.h>

#define F_CPU 16000000		// 8 MHz clock speed
#define BAUD 9600			// define baud

#define BAUDRATE ((F_CPU)/(BAUD*16UL)-1)

#include <util/delay.h>
#include "hx711.h"

#include "LCD.h"

double current_weight,x=0;



int main(void)
{

	/// Setup
	LCD_init();
	HX711_init(128);
	//HX711_set_scale(11500.f);
 HX711_set_scale(1.5f/*242300.88*/);
	HX711_tare(10);

	char acc_conv_out_x[10]; // Output for dtostrf (+1 for null char)
	
	/// Main Loop
	while(1)
	{
		// Testing HX711 output
		current_weight = HX711_get_units(10);
		if(x!=current_weight)
		{
			LCD_clear();
			LCD_writeInt(current_weight);
			x=current_weight;
		}
		//LCD_writeInt(current_weight);
		_delay_ms(500);
		//LCD_clear();
	}
}