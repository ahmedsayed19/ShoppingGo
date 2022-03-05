/*
 * DIO.c
 *
 * Created: 29/10/2020 14:23:51
 *  Author: 20114
 */ 
#include "DIO.h"
//#include "BIT_MATH.h"
//#include <avr/io.h>


void DIO_SET_PORT_DIR(uint8 port,uint8 dir)
{
	switch(port)
	{
		case DIO_PORTA:
		  DDRA=dir;
		  break;
		  
		case DIO_PORTB:
		  DDRB=dir;
		  break;
		  
		case DIO_PORTC:
		  DDRC=dir;
		  break;
		  
		case DIO_PORTD:
		  DDRD=dir;
		  break;
		  
		  default:
		  break;
				
	}
}

void DIO_SET_BIN_DIR(uint8 port,uint8 pin,uint8 dir)
{
	 if(dir==DIO_BIN_OUTPUT)
	 {
		 switch(port)
		 {
			 case DIO_PORTA:
			 SET_BIT(DDRA,pin);
			 break;
			 
			 case DIO_PORTB:
			 SET_BIT(DDRB,pin);
			 break;
			 
			 case DIO_PORTC:
			 SET_BIT(DDRC,pin);
			 break;
			 
			 case DIO_PORTD:
			 SET_BIT(DDRD,pin);
			 break;
			 
			 default:
			 break;
			 
		 }
	 
	 }	 
	 else
	 {
		  switch(port)
		  {
			  case DIO_PORTA:
			 CLR_BIT(DDRA,pin);
			  break;
			  
			  case DIO_PORTB:
			  CLR_BIT(DDRB,pin);
			  break;
			  
			  case DIO_PORTC:
			  CLR_BIT(DDRC,pin);
			  break;
			  
			  case DIO_PORTD:
			  CLR_BIT(DDRD,pin);
			  break;
			  
			  default:
			  break;
			  
		  }
	 }
}

void DIO_SET_PORT_VAL(uint8 port,uint8 val)
{
	switch(port)
	{
		case DIO_PORTA:
		PORTA=val;
		break;
		
		case DIO_PORTB:
		PORTB=val;
		break;
		
		case DIO_PORTC:
		PORTC=val;
		break;
		
		case DIO_PORTD:
		PORTD=val;
		break;
		
		default:
		break;
	}
}
void DIO_SET_BIN_VAL(uint8 port,uint8 pin,uint8 val)
{
	switch(port)
	{
		case DIO_PORTA:
		if(val==DIO_BIN_HIGT)
		   SET_BIT(PORTA,pin);
		else
		   CLR_BIT(PORTA,pin);
		break;
		
		case DIO_PORTB:
		if(val==DIO_BIN_HIGT)
		   SET_BIT(PORTB,pin);
		else
		   CLR_BIT(PORTB,pin);
		break;
		
		case DIO_PORTC:
		if(val==DIO_BIN_HIGT)
		   SET_BIT(PORTC,pin);
		else
		   CLR_BIT(PORTC,pin);
		break;
		
		case DIO_PORTD:
		if(val==DIO_BIN_HIGT)
		   SET_BIT(PORTD,pin);
		else
		   CLR_BIT(PORTD,pin);
		break;
		
		default:
		break;
		
	}
}

void DIO_GET_PORT_VAL(uint8 port,uint8* val)
{
		switch(port)
		{
			case DIO_PORTA:
			*val=PINA;
			break;
			
			case DIO_PORTB:
			*val=PINB;
			break;
			
			case DIO_PORTC:
			*val=PINC;
			break;
			
			case DIO_PORTD:
			*val=PIND;
			break;
			
			default:
			break;
		}
}
void DIO_GET_BIN_VAL(uint8 port,uint8 pin,uint8* val)
{
	switch(port)
	{
		case DIO_PORTA:
		*val=GET_BIT(PINA,pin);
		break;
		
		case DIO_PORTB:
		*val=GET_BIT(PINB,pin);
		break;
		
		case DIO_PORTC:
		*val=GET_BIT(PINC,pin);
		break;
		
		case DIO_PORTD:
		*val=GET_BIT(PIND,pin);
		break;
		
		default:
		break;
	}
}

void DIO_TOGGLE_BIN(uint8 port,uint8 pin)
{
	switch(port)
	{
		case DIO_PORTA:
		TOGGLE_BIT(PORTA,pin);
		break;
		
		case DIO_PORTB:
		TOGGLE_BIT(PORTB,pin);
		break;
		
		case DIO_PORTC:
		TOGGLE_BIT(PORTC,pin);
		break;
		
		case DIO_PORTD:
		TOGGLE_BIT(PORTD,pin);
		break;
		
		default:
		break;
	}
}
