import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material/sort';
import { BoatService } from './boat.service';

@Component({
  selector: 'app-boats',
  templateUrl: './boats.component.html',
  styleUrls: ['./boats.component.css']
})
export class BoatsComponent implements OnInit {

  boats: any[] = []
  sortedData: any[] = []

  constructor(private _boatService: BoatService) { }

  ngOnInit(): void {
    this.getBoats();
  }

  getBoats() {
    this._boatService.getBoats().subscribe(
      boats => {
        this.boats = boats;
        this.sortedData = this.boats.slice();
      }
    )
  }

  sortData(sort: Sort) {
    const data = this.boats.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name': return compare(a.name, b.name, isAsc);
        case 'address': return compare(a.address, b.address, isAsc);
        case 'capacity': return compare(a.capacity, b.capacity, isAsc);
        case 'boatOwner': return compare(a.boatOwner.email, b.boatOwner.email, isAsc);
        default: return 0;
      }
    });
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
