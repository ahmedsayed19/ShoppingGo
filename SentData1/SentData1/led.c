/*
 * led.c
 *
 * Created: 2/14/2022 6:45:20 PM
 *  Author: Qaiaty
 */ 
#include "led.h"

void Led0_INIT(void)
{
	DIO_SET_BIN_DIR(Led0Port,Led0Bit,DIO_BIN_OUTPUT);
}
void Led0_ON(void)
{
	DIO_SET_BIN_VAL(Led0Port,Led0Bit,DIO_BIN_HIGT);
}
void Led0_OFF(void)
{
	DIO_SET_BIN_VAL(Led0Port,Led0Bit,DIO_BIN_LOW);
}
void Led0_Toggel(void)
{
	DIO_TOGGLE_BIN(Led0Port,Led0Bit);
}
///////////////////////////
void Led1_INIT(void)
{
	DIO_SET_BIN_DIR(Led1Port,Led1Bit,DIO_BIN_OUTPUT);
	//  DIO_SET_BIN_DIR(PORTA,2,DIO_BIN_OUTPUT);
}
void Led1_ON(void)
{
	DIO_SET_BIN_VAL(Led1Port,Led1Bit,DIO_BIN_HIGT);
}
void Led1_OFF(void)
{
	DIO_SET_BIN_VAL(Led1Port,Led1Bit,DIO_BIN_LOW);
}
void Led1_Toggel(void)
{
	DIO_TOGGLE_BIN(Led1Port,Led1Bit);
	//DIO_TOGGLE_BIN(PORTA,2);
}

////////////////////////////////
void Led2_INIT(void)
{
	DIO_SET_BIN_DIR(Led2Port,Led2Bit,DIO_BIN_OUTPUT);
}
void Led2_ON(void)
{
	DIO_SET_BIN_VAL(Led2Port,Led2Bit,DIO_BIN_HIGT);
}
void Led2_OFF(void)
{
	DIO_SET_BIN_VAL(Led2Port,Led2Bit,DIO_BIN_LOW);
}
void Led2_Toggel(void)
{
	DIO_TOGGLE_BIN(Led2Port,Led2Bit);
}