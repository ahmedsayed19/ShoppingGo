/*
 * led.h
 *
 * Created: 2/14/2022 6:45:36 PM
 *  Author: Qaiaty
 */ 


#ifndef LED_H_
#define LED_H_

#define Led0Port   DIO_PORTC
#define Led1Port   DIO_PORTC
#define Led2Port   DIO_PORTD

#define Led0Bit    2
#define Led1Bit    7
#define Led2Bit    3



#include "DIO.h"

void Led0_INIT(void);
void Led0_ON(void);
void Led0_OFF(void);
void Led0_Toggel(void);

void Led1_INIT(void);
void Led1_ON(void);
void Led1_OFF(void);
void Led1_Toggel(void);

void Led2_INIT(void);
void Led2_ON(void);
void Led2_OFF(void);
void Led2_Toggel(void);

#endif /* LED_H_ */