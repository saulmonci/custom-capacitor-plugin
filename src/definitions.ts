declare module '@capacitor/core'{
  interface PluginRegisry{
    ExamplePlugin:ExamplePlugin
  }
}


export interface ExamplePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  
  showAlert(options:{msg:string}):Promise<any>;

  print(options:{ 
    host: string,
    shareName: string,
    domain:string,
    username: string,
    password: string,
    dataToPrint: any
  }):Promise<any>;

  printTcp(options:{
    host:string,
    dataToPrint: any
  }):Promise<any>;
}
