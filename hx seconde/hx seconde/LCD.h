/*
 * LCD.h
 *
 * Created: 02/11/2020 18:10:00
 *  Author: 20114
 */ 


#ifndef LCD_H_
#define LCD_H_

#include "LCD_CFG.h"

#define F_CPU 16000000
#include <util/delay.h>


void LCD_init(void);
void LCD_writeCommand(uint8 cmd);
void LCD_writeChar(uint8 chr);
void LCD_writeString(uint8* str);
void LCD_clear(void);
void LCD_Go_To(uint8 row,uint8 col);
void LCD_writeInt(uint32 num);
void LCD_writedouble(double num);














#endif /* LCD_H_ */