/*
 * UART.c
 *
 * Created: 12/12/2020 21:07:36
 *  Author: 20114
 */ 


#include "UART.h"
#include "DIO.h"

#define freq 160000   /* 16000000 */
#define BOUD_RATe 96  /* 9600 */

void UART_init(void)
{
	uint8 BR_value=0;
	/* init bins of tx and rx */
	DIO_SET_BIN_DIR(DIO_PORTD,DIO_PIN0,DIO_BIN_INPUT);
	DIO_SET_BIN_DIR(DIO_PORTD,DIO_PIN1,DIO_BIN_OUTPUT);
	
	BR_value=((freq/(16*BOUD_RATe))-1);
	UBRRL=BR_value;   
	
	SET_BIT(UCSRB,3);
	SET_BIT(UCSRB,4);
	
	UCSRC=0x86;
	
}

void UART_TX(uint8 data)
{
	UDR=data;
	while(GET_BIT(UCSRA,5)==0);
}

void UART_TX_string(uint8* str)
{
	uint8 i=0;
	while(str[i]!='\0')
	{
		UART_TX(str[i]);
		i++;
	}
}

uint8 UART_RX(void)
{
    while(GET_BIT(UCSRA,7)==0);
	
	return UDR;	
}