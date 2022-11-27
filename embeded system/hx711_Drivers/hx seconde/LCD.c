/*
 * LCD.c
 *
 * Created: 02/11/2020 18:09:43
 *  Author: 20114
 */ 
#include "LCD.h"

void LCD_init(void)
{
	#if LCD_MODE == 8
	DIO_SET_BIN_DIR(LCD_8BIT_CMD_PORT,LCD_RS_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_8BIT_CMD_PORT,LCD_RW_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_8BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_OUTPUT);
	
	DIO_SET_PORT_DIR(LCD_8BIT_DATA_PORT,DIO_PORT_OUTPUT);
	
	_delay_ms(100);
	
	LCD_writeCommand(0x38);
	LCD_writeCommand(0x01);  /* clear */
	LCD_writeCommand(0x06);  /* left to right */
	LCD_writeCommand(0x0c);  /* cursor off */
	LCD_writeCommand(0x02);  /* start from first */
	_delay_ms(100);
	#elif LCD_MODE ==4
	DIO_SET_BIN_DIR(LCD_4BIT_CMD_PORT,LCD_RS_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_4BIT_CMD_PORT,LCD_RW_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_OUTPUT);
	
	DIO_SET_BIN_DIR(LCD_4BIT_DATA_PORT,D4_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_4BIT_DATA_PORT,D5_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_4BIT_DATA_PORT,D6_PIN,DIO_BIN_OUTPUT);
	DIO_SET_BIN_DIR(LCD_4BIT_DATA_PORT,D7_PIN,DIO_BIN_OUTPUT);
	
	_delay_ms(100);
	
	LCD_writeCommand(0x33);
	LCD_writeCommand(0x32);
	LCD_writeCommand(0x28);
	
	LCD_writeCommand(0x0c);
	LCD_writeCommand(0x01);
	LCD_writeCommand(0x06); 
	LCD_writeCommand(0x02);
	_delay_ms(100);
	
	#endif
}

void LCD_writeCommand(uint8 cmd)
{
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_RS_PIN,DIO_BIN_LOW);
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_RW_PIN,DIO_BIN_LOW);
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_LOW);
	
	LCD_write_port=(cmd&(0xf0))|(LCD_write_port&(0x0f));
	
	
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_HIGT);
	_delay_ms(1);
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_LOW);
	
	LCD_write_port=(cmd<<4)|(LCD_write_port&(0x0f));
	
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_HIGT);
	_delay_ms(1);
	DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_LOW);
	
	_delay_ms(5);
			
}

void LCD_writeChar(uint8 chr)
{
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_RS_PIN,DIO_BIN_HIGT);
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_RW_PIN,DIO_BIN_LOW);
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_LOW);
		
		LCD_write_port=(chr&(0xf0))|(LCD_write_port&(0x0f));
		
		
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_HIGT);
		_delay_ms(1);
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_LOW);
		
		LCD_write_port=(chr<<4)|(LCD_write_port&(0x0f));
		
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_HIGT);
		_delay_ms(1);
		DIO_SET_BIN_VAL(LCD_4BIT_CMD_PORT,LCD_E_PIN,DIO_BIN_LOW);
		
		_delay_ms(5);
		
}

void LCD_clear(void)
{
	LCD_writeCommand(0x01);
}

void LCD_writeString(uint8* str)
{
	uint8 i=0;
	while(str[i] !='\0')
	{
		LCD_writeChar(str[i]);
		i++;
	}
}

void LCD_Go_To(uint8 row,uint8 col)
{
	uint8 pos[2]={0x80,0xc0};
	LCD_writeCommand(pos[row]+col);
}

void LCD_writeInt(uint32 num)
{
	uint8 arr[10],con=0;
	
	while(num)
	{
		arr[con]=(num%10)+48;
		con++;
		num/=10;
	}
	for(int i=con-1;i>=0;i--)
	{
		LCD_writeChar(arr[i]);
	}
	
}
void LCD_writedouble(double num)
{
	uint32 base=num,vis;
	uint8 vis2[5],coun=0;
	vis=(num-base)*100000;
	LCD_writeInt(base);
	LCD_writeChar('.');
	while(vis)
	{
		vis2[coun]=vis%10;
		vis/=10;
		coun++;
	}
	coun=1;
	for(int i=3;i>=0;i--)
	{
		if(vis2[i]==9&&vis2[i+1]!=9)
		{
			vis2[i+1]++;
			break;
		}
		coun++;
	}
	uint8 ind=4;
	while (coun--)
	{
		LCD_writeInt(vis2[ind]);
		ind--;
	}
}