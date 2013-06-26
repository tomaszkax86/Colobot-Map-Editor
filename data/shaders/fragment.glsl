#version 110

uniform sampler2D texture;
varying float brightness;

vec4 temp_Color;
float temp_Alpha;

void main()
{
    temp_Color = texture2D(texture, gl_TexCoord[0].st);
    temp_Alpha = temp_Color.a;
    gl_FragColor = brightness * temp_Color;
}
