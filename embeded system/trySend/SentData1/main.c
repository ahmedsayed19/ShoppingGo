/*
 * interrupt_2022.c
 *
 * Created: 2/14/2022 5:12:48 PM
 * Author : salem
 */ 

#include "External_interrupt.h"
#include "led.h"
#include "LCD.h"
#include "UART.h"
#include <avr/interrupt.h>

uint8 coun=0;

int main(void)
{
    ExternalInterrupt0_INIT();
	LCD_init();
	UART_init();
	Led0_INIT();
	//Led1_INIT();
	//Led2_INIT();
    while (1) 
    {
    }
}

ISR(INT0_vect)
{
	Led0_Toggel();
	//Led1_Toggel();
	//Led2_Toggel();
	LCD_clear();
	LCD_writeInt(coun);
	UART_TX(coun);
	coun++;
}