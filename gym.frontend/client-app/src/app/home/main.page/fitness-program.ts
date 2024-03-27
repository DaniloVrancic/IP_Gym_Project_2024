import { FitnessProgramType } from "./fitness-program-type";
import { User } from "../../user";

export interface FitnessProgram {
  id: number;
  name: string;
  description: string;
  locationOfEvent: string;
  price: number;
  duration: number;
  difficultyLevel: number;
  imageUrl: string;
  status: number;
  fitnessProgramType: FitnessProgramType;
  user_creator: User;
}