/*
 * barcode_first.c
 *
 * Created: 2/26/2022 6:23:04 PM
 * Author :salem
 */ 

//#include <avr/io.h>
#include <util/delay.h>
#include "LCD.h"
#include "UART.h"


int main(void)
{
    LCD_init();
	UART_init();
	uint8 data1=-1;
	uint8 d[15];
	uint32 coun=0;
    while (1) 
    {
		data1=UART_RX();
		if(data1>=48&&data1<=123)
		{
			d[coun%15]=data1;
			coun++;
		}
		else
		{
			LCD_clear();
			for(uint8 i=0;i<coun;i++)
			{
				LCD_writeChar(d[i]);
			}
			coun=0;
		}
		/*if(data1!=-1)
		{
			d[coun%15]=data1;
			coun++;
			//LCD_writeChar(data1);
		}
		if(coun%15==0)
		{
			LCD_clear();
		   LCD_writeString(d);
		}
		data1=-1;
		*/
		
    }
}

