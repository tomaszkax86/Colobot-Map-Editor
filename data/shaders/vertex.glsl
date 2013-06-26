#version 110

uniform vec3 light;
varying float brightness;

void main()
{
    gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
    
    gl_TexCoord[0] = gl_MultiTexCoord0;
    
    brightness = 0.5f + 0.5f * dot(light, gl_Normal) / (length(light) * length(gl_Normal));
}
