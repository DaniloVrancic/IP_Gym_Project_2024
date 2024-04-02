import { FitnessProgramType } from "./fitness-program-type";
import { User } from "../../user";

export interface FitnessProgram {
  id: number | null;
  name: string | null;
  description: string | null;
  locationOfEvent: string | null;
  price: number | null;
  duration: number | null;
  difficultyLevel: number | null;
  imageUrl: string | null;
  status: number | null;
  fitnessProgramType: FitnessProgramType | null;
  user_creator: User | null;
}