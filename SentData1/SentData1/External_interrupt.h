/*
 * External_interrupt.h
 *
 * Created: 2/14/2022 5:16:06 PM
 *  Author: Qaiaty
 */ 


#ifndef EXTERNAL_INTERRUPT_H_
#define EXTERNAL_INTERRUPT_H_

#include "External_interrupt_CFG.h"

#define GLOBAL_INTERRUPT_ENABLE 1
#define GLOBAL_INTERRUPT_DISABLE 0

#define INT_TRIGGER_LOW_LEVEL          0
#define INT_TRIGGER_RISING_EDGE        1
#define INT_TRIGGER_FALLING_EDGE       2
#define INT_TRIGGER_ANY_LOGICAL_CHANGE 3 


void Set_GlobalInterrupt(void);

void ExternalInterrupt0_INIT(void);
void ExternalInterrupt1_INIT(void);
void ExternalInterrupt2_INIT(void);






#endif /* EXTERNAL_INTERRUPT_H_ */