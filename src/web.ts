import { WebPlugin } from '@capacitor/core';

import type { ExamplePlugin } from './definitions';

export class ExampleWeb extends WebPlugin implements ExamplePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async showAlert(options: { msg: string; }): Promise<any> {
    alert(options.msg);
    return options.msg
  }

  async print(options: { host: string; shareName: string; domain: string; username: string; password: string; dataToPrint: string; }): Promise<any> {
      return 'Solo android: '+ JSON.stringify(options);
  }

  async printTcp(options: { host: string; dataToPrint: any; }): Promise<any> {
      return 'Solo android' + JSON.stringify(options);
  }
}
