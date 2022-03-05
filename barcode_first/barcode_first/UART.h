/*
 * UART.h
 *
 * Created: 12/12/2020 21:07:56
 *  Author: 20114
 */ 


#ifndef UART_H_
#define UART_H_

#include "ATmega32_Rrgiosters.h"
#include "BIT_MATH.h"



void UART_init(void);

void UART_TX(uint8 data);

void UART_TX_string(uint8* str);

uint8 UART_RX(void);






#endif /* UART_H_ */