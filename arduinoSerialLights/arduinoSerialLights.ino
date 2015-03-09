#include <PololuLedStrip.h>

// Create an ledStrip object and specify the pin it will use.
PololuLedStrip<12> ledStrip;

// Create a buffer for holding the colors (3 bytes per color).
#define LED_COUNT 120
rgb_color colors[LED_COUNT];

char rgbIn[3];

void setup()
{
  // Start up the serial port, for communication with the PC.
  Serial.begin(115200);
  Serial.println(" Ready to receive colors!!"); 
  //Serial.setTimeout(0);

  testLights();
}

void loop()
{

  if (Serial.available())
  {
    for(int i=0; i < LED_COUNT; i++){
      Serial.readBytes(rgbIn,3);


      colors[i].red = rgbIn[0];
      colors[i].green = rgbIn[1];
      colors[i].blue = rgbIn[2];

    }

    ledStrip.write(colors, LED_COUNT);

  }

  //delay(100);
}

void testLights(){


  for(int i=0; i < LED_COUNT; i++){

    for(int j=0; j < LED_COUNT; j++){
      if(j==i){
        colors[j].red = 255;
        colors[j].green = 255;
        colors[j].blue = 255;
      }
      else {
        colors[j].red = 0;
        colors[j].green = 0;
        colors[j].blue = 0;
      }
    }


    delay(100);
    ledStrip.write(colors, LED_COUNT);

  }

}















