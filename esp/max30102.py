from machine import I2C,Pin
import sys
import gc

# i2c 地址
I2C_WRITE_ADDR = 0xAE
I2C_READ_ADDR = 0xAF

#设备内部寄存器地址
REG_INTR_STATUS_1 = 0x00
REG_INTR_STATUS_2 = 0x01

REG_INTR_ENABLE_1 = 0x02
REG_INTR_ENABLE_2 = 0x03

REG_FIFO_WR_PTR = 0x04
REG_OVF_COUNTER = 0x05
REG_FIFO_RD_PTR = 0x06
REG_FIFO_DATA = 0x07
REG_FIFO_CONFIG = 0x08

REG_MODE_CONFIG = 0x09
REG_SPO2_CONFIG = 0x0A
REG_LED1_PA = 0x0C

REG_LED2_PA = 0x0D
REG_PILOT_PA = 0x10
REG_MULTI_LED_CTRL1 = 0x11
REG_MULTI_LED_CTRL2 = 0x12

REG_TEMP_INTR = 0x1F
REG_TEMP_FRAC = 0x20
REG_TEMP_CONFIG = 0x21
REG_PROX_INT_THRESH = 0x30
REG_REV_ID = 0xFE
REG_PART_ID = 0xFF

class MAX30102():
    #默认使用引脚X1作为中断引脚，连接模块的INT引脚
    #默认使用TPYBoard v102的i2c(2)接口 SCL=>Y9 SDA=>Y10
    def __init__(self):
        self.i2c = I2C(scl=Pin(5), sda=Pin(4), freq=10000)
        self.address = 0x57
        #设置中断引脚为输入模式
        self.setup()                                                     #软复位
        
        

    def shutdown(self):
        """
        关闭模块
        """
        self.i2c.writeto_mem(self.address, REG_MODE_CONFIG,b'\x80')

    def reset(self):
        """
        Reset the device, this will clear all settings,
        so after running this, run setup() again.
        """
        self.i2c.writeto_mem(self.address, REG_MODE_CONFIG,b'\x40')

    def setup(self, led_mode=b'\x03'):
        """
        模块的初始化设置
        """

        self.i2c.writeto_mem(self.address, REG_INTR_ENABLE_1,b'\xc0')
        self.i2c.writeto_mem(self.address, REG_INTR_ENABLE_2,b'\x00')
        self.i2c.writeto_mem(self.address, REG_FIFO_WR_PTR,b'\x00')
        self.i2c.writeto_mem(self.address, REG_OVF_COUNTER,b'\x00')
        self.i2c.writeto_mem(self.address, REG_FIFO_RD_PTR,b'\x00')
        self.i2c.writeto_mem(self.address, REG_FIFO_CONFIG,b'\x4f')
        self.i2c.writeto_mem(self.address, REG_MODE_CONFIG, led_mode)
        self.i2c.writeto_mem(self.address, REG_SPO2_CONFIG,b'\x27')
        self.i2c.writeto_mem(self.address, REG_LED1_PA, b'\x24')
        self.i2c.writeto_mem(self.address, REG_LED2_PA, b'\x24')
        self.i2c.writeto_mem(self.address, REG_PILOT_PA, b'\x7f')


    def set_config(self, reg, value):
        self.i2c.writeto_mem(self.address, reg, value)

    def read_fifo(self):
        """
        读取寄存器的数据
        """
        red_led = None
        ir_led = None

        #从寄存器中读取1个字节的数据
        # reg_INTR1 = self.i2c.readfrom_mem(self.address, REG_INTR_ENABLE_1,1)
        # reg_INTR2 = self.i2c.readfrom_mem(self.address, REG_INTR_ENABLE_2,1)
        # print (reg_INTR1,reg_INTR2)
        d = self.i2c.readfrom_mem(self.address, REG_FIFO_DATA,6)

        # mask MSB [23:18]
        red_led = (d[0] << 16 | d[1] << 8 | d[2]) & 0x03FFFF
        ir_led = (d[3] << 16 | d[4] << 8 | d[5]) & 0x03FFFF

        return red_led, ir_led
    def read_sequential(self, amount=100):
        """
        读取模块上红色LED和红外光LED测量的数据
        """
        red_buf = []
        ir_buf = []
        for i in range(amount):
            red, ir = self.read_fifo()

            red_buf.append(red)
            ir_buf.append(ir)



        return red_buf, ir_buf
