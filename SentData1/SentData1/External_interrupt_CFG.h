/*
 * External_interrupt_CFG.h
 *
 * Created: 2/14/2022 5:16:59 PM
 *  Author: Qaiaty
 */ 


#ifndef EXTERNAL_INTERRUPT_CFG_H_
#define EXTERNAL_INTERRUPT_CFG_H_

#include "ATmega32_Rrgiosters.h"
#include "BIT_MATH.h"

#define  GLOBAL_INTERRUPT_STATE         GLOBAL_INTERRUPT_ENABLE

#define EXTERNAL_INTERRUPT0_TRIGGER     INT_TRIGGER_RISING_EDGE
#define EXTERNAL_INTERRUPT1_TRIGGER		INT_TRIGGER_FALLING_EDGE
#define EXTERNAL_INTERRUPT2_TRIGGER		INT_TRIGGER_FALLING_EDGE


#endif /* EXTERNAL_INTERRUPT_CFG_H_ */