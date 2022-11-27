/*
 * DIO.h
 *
 * Created: 29/10/2020 14:23:37
 *  Author: 20114
 */ 


#ifndef DIO_H_
#define DIO_H_

#include "ATmega32_Rrgiosters.h"
#include "BIT_MATH.h"


#define DIO_PORTA 0
#define DIO_PORTB 1
#define DIO_PORTC 2
#define DIO_PORTD 3


#define DIO_PIN0 0
#define DIO_PIN1 1
#define DIO_PIN2 2
#define DIO_PIN3 3
#define DIO_PIN4 4
#define DIO_PIN5 5
#define DIO_PIN6 6
#define DIO_PIN7 7


#define DIO_BIN_OUTPUT 1
#define DIO_BIN_INPUT 0

#define DIO_PORT_OUTPUT 0xff
#define DIO_PORT_INPUT 0x00


#define DIO_BIN_HIGT 1
#define DIO_BIN_LOW 0

#define DIO_PORT_HIGT 0xff
#define DIO_PORT_LOW 0x00




void DIO_SET_PORT_DIR(uint8 port,uint8 dir);
void DIO_SET_BIN_DIR(uint8 port,uint8 pin,uint8 dir);

void DIO_SET_PORT_VAL(uint8 port,uint8 val);
void DIO_SET_BIN_VAL(uint8 port,uint8 pin,uint8 val);

void DIO_GET_PORT_VAL(uint8 port,uint8* val);
void DIO_GET_BIN_VAL(uint8 port,uint8 pin,uint8* val);

void DIO_TOGGLE_BIN(uint8 port,uint8 pin);




#endif /* DIO_H_ */